package kr.mashup.branding.facade.danggn;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.pushnoti.vo.DanggnFirstRecordMemberUpdatedVo;
import kr.mashup.branding.domain.pushnoti.vo.DanggnFirstRecordPlatformUpdatedVo;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.danggn.DanggnCacheKey;
import kr.mashup.branding.service.danggn.DanggnCacheService;
import kr.mashup.branding.service.danggn.DanggnScoreService;
import kr.mashup.branding.service.danggn.DanggnShakeLogService;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.danggn.response.DanggnMemberRankResponse;
import kr.mashup.branding.ui.danggn.response.DanggnPlatformRankResponse;
import kr.mashup.branding.ui.danggn.response.DanggnScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DanggnFacadeService {
    @Value("${golden-danggn-percent}")
    private Integer goldenDanggnPercent;
    private final MemberService memberService;

    private final DanggnShakeLogService danggnShakeLogService;

    private final DanggnScoreService danggnScoreService;

    private final DanggnCacheService danggnCacheService;

    private final GenerationService generationService;

    private final PushNotiEventPublisher pushNotiEventPublisher;

    @Transactional
    public DanggnScoreResponse addScore(
        Long memberId,
        Integer generationNumber,
        Long score
    ) {
        final MemberGeneration memberGeneration = memberService.findByMemberIdAndGenerationNumber(memberId, generationNumber);
        DanggnScore danggnScore = danggnScoreService.findByMemberGeneration(memberGeneration);
        danggnScore.addScore(score);
        danggnShakeLogService.createLog(memberGeneration, score);
        return DanggnScoreResponse.of(danggnScore.getTotalShakeScore());
    }

    @Transactional(readOnly = true)
    public List<DanggnMemberRankResponse> getMemberRankList(Integer generationNumber) {
        return danggnScoreService.getDanggnScoreOrderedList(generationNumber)
            .stream().map(DanggnMemberRankResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DanggnPlatformRankResponse> getPlatformRankList(Integer generationNumber) {
        List<DanggnPlatformRankResponse> existingPlatformRankList = getExistingPlatformRankList(generationNumber);

        Set<Platform> notExistingPlatforms = getNotExistingPlatforms(existingPlatformRankList);
        List<DanggnPlatformRankResponse> notExistingPlatformRankList = notExistingPlatforms.stream()
            .map(platform -> DanggnPlatformRankResponse.of(platform, 0L))
            .collect(Collectors.toList());

        return Stream.concat(existingPlatformRankList.stream(), notExistingPlatformRankList.stream()).collect(Collectors.toList());
    }

    public Integer getGoldenDanggnPercent() {
        return goldenDanggnPercent;
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 0)
    @Transactional(readOnly = true)
    public void sendDanggnFirstRecordMemberUpdatedPushNoti() {
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());
        if (generations.isEmpty())
            return;

        generations.forEach(
                generation -> {
                    Integer generationNumber = generation.getNumber();
                    String currentFirstRecordMemberId = danggnCacheService.findFirstRecordMemberId(generationNumber);
                    String cachedFirstRecordMemberId = danggnCacheService.getCachedFirstRecord(DanggnCacheKey.MEMBER, generationNumber);

                    if (currentFirstRecordMemberId == null || cachedFirstRecordMemberId.equals(currentFirstRecordMemberId)) {
                        return;
                    }
                    // 변경된 부분 있으면 개인 랭킹 1 업데이트 푸시 알림 보낸 후 캐시 업데이트
                    pushNotiEventPublisher.publishPushNotiSendEvent(new DanggnFirstRecordMemberUpdatedVo(memberService.getAllDanggnPushNotiTargetableMembers()));
                    danggnCacheService.updateCachedFirstRecord(DanggnCacheKey.MEMBER, generationNumber, currentFirstRecordMemberId);
                }
        );
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 0)
    @Transactional(readOnly = true)
    public void sendDanggnFirstRecordPlatformPushNoti() {
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());
        if (generations.isEmpty())
            return;

        generations.forEach(
                generation -> {
                    Integer generationNumber = generation.getNumber();
                    String currentFirstRecordPlatform = danggnCacheService.findFirstRecordPlatform(generationNumber);
                    String cachedFirstRecordPlatform = danggnCacheService.getCachedFirstRecord(DanggnCacheKey.PLATFORM, generationNumber);

                    if (currentFirstRecordPlatform == null || cachedFirstRecordPlatform.equals(currentFirstRecordPlatform)) {
                        return;
                    }
                    // 변경된 부분 있으면 개인 팀 1 업데이트 푸시 알림 보낸 후 캐시 업데이트
                    pushNotiEventPublisher.publishPushNotiSendEvent(new DanggnFirstRecordPlatformUpdatedVo(memberService.getAllDanggnPushNotiTargetableMembers()));
                    danggnCacheService.updateCachedFirstRecord(DanggnCacheKey.PLATFORM, generationNumber, currentFirstRecordPlatform);
                }
        );
    }

    private List<DanggnPlatformRankResponse> getExistingPlatformRankList(Integer generationNumber) {
        return danggnScoreService.getDanggnScorePlatformOrderedList(generationNumber).stream()
            .map(queryResult -> DanggnPlatformRankResponse.of(queryResult.getPlatform(), queryResult.getTotalScore()))
            .collect(Collectors.toList());
    }

    private Set<Platform> getNotExistingPlatforms(List<DanggnPlatformRankResponse> existingPlatformRankList) {
        Set<Platform> entirePlatforms = Arrays.stream(Platform.values()).collect(Collectors.toSet());
        Set<Platform> existingPlatforms = existingPlatformRankList.stream()
            .map(DanggnPlatformRankResponse::getPlatform)
            .collect(Collectors.toSet());
        entirePlatforms.removeAll(existingPlatforms);
        return entirePlatforms;
    }
}
