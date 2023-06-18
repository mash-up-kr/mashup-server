package kr.mashup.branding.facade.danggn;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.danggn.DanggnNotificationMemberRecord;
import kr.mashup.branding.domain.danggn.DanggnNotificationPlatformRecord;
import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.pushnoti.vo.DanggnFirstRecordMemberUpdatedVo;
import kr.mashup.branding.domain.pushnoti.vo.DanggnFirstRecordPlatformUpdatedVo;
import kr.mashup.branding.domain.pushnoti.vo.DanggnNotificationMemberRecordUpdatedVo;
import kr.mashup.branding.domain.pushnoti.vo.DanggnNotificationPlatformRecordUpdatedVo;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.repository.danggn.DanggnScoreRepositoryCustomImpl;
import kr.mashup.branding.service.danggn.DanggnCacheKey;
import kr.mashup.branding.service.danggn.DanggnCacheService;
import kr.mashup.branding.service.danggn.DanggnNotificationRecordService;
import kr.mashup.branding.service.danggn.DanggnNotificationSentUnit;
import kr.mashup.branding.service.danggn.DanggnRankingRoundService;
import kr.mashup.branding.service.danggn.DanggnScoreService;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DanggnNotiFacadeService {

    private final MemberService memberService;

    private final DanggnScoreService danggnScoreService;

    private final DanggnCacheService danggnCacheService;

    private final GenerationService generationService;

    private final DanggnNotificationRecordService danggnNotificationRecordService;

    private final PushNotiEventPublisher pushNotiEventPublisher;

    private final DanggnRankingRoundService danggnRankingRoundService;

    @Scheduled(cron = "0 0 09,13,19 * * *")
    @Transactional(readOnly = true)
    public void sendDanggnFirstRecordMemberUpdatedPushNoti() {
        // 현재 활동하는 기수 조회
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());

        generations.forEach(
                generation -> {
                    Integer generationNumber = generation.getNumber();
                    DanggnRankingRound currentDanggnRankingRound = danggnRankingRoundService.findCurrentByGeneration(generationNumber);

                    Member currentFirstRecordMember = danggnCacheService.findFirstRecordMember(generationNumber, currentDanggnRankingRound.getId());
                    String currentFirstRecordMemberId = currentFirstRecordMember.getId().toString();
                    String cachedFirstRecordMemberId = danggnCacheService.getCachedFirstRecordMemberId(generationNumber, currentDanggnRankingRound.getId());

                    if (currentFirstRecordMemberId.equals(cachedFirstRecordMemberId)) {
                        return;
                    }
                    // 변경된 부분 있으면 업데이트 푸시 알림 보낸 후 캐시 업데이트
                    pushNotiEventPublisher.publishPushNotiSendEvent(new DanggnFirstRecordMemberUpdatedVo(currentFirstRecordMember, memberService.getAllDanggnPushNotiTargetableMembers()));
                    danggnCacheService.updateCachedFirstRecord(DanggnCacheKey.MEMBER, generationNumber, currentFirstRecordMemberId);
                }
        );
    }

    @Scheduled(cron = "0 0 09,13,19 * * *")
    @Transactional(readOnly = true)
    public void sendDanggnFirstRecordPlatformPushNoti() {
        // 현재 활동하는 기수 조회
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());

        generations.forEach(
            generation -> {
                Integer generationNumber = generation.getNumber();
                DanggnRankingRound currentDanggnRankingRound = danggnRankingRoundService.findCurrentByGeneration(generationNumber);
                Platform currentFirstRecordPlatform = danggnCacheService.findFirstRecordPlatform(generationNumber, currentDanggnRankingRound.getId());
                String cachedFirstRecordPlatform = danggnCacheService.getCachedFirstRecordPlatform(generationNumber, currentDanggnRankingRound.getId());

                if (currentFirstRecordPlatform.toString().equals(cachedFirstRecordPlatform)) {
                    return;
                }
                // 변경된 부분 있으면  업데이트 푸시 알림 보낸 후 캐시 업데이트
                pushNotiEventPublisher.publishPushNotiSendEvent(
                    new DanggnFirstRecordPlatformUpdatedVo(currentFirstRecordPlatform, memberService.getAllDanggnPushNotiTargetableMembers()));
                danggnCacheService.updateCachedFirstRecord(DanggnCacheKey.PLATFORM, generationNumber,
                    currentFirstRecordPlatform.toString());
            }
        );
    }

    @Scheduled(initialDelay = 0, fixedDelay = 60 * 1000)
    @Transactional
    public void sendDanggnMemberRecordPushNoti() {
        // 현재 활동하는 기수 조회
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());

        generations.forEach(
            generation -> {
                Integer generationNumber = generation.getNumber();
                Long currentDanggnRankingRoundId = danggnRankingRoundService.findCurrentByGeneration(generationNumber).getId();
                Map<MemberGeneration, Long> scores = danggnScoreService.getDanggnScoreOrderedList(generationNumber, currentDanggnRankingRoundId)
                    .stream()
                    .collect(Collectors.toMap(DanggnScore::getMemberGeneration, DanggnScore::getTotalShakeScore));
                List<MemberGeneration> memberGenerations = memberService.findByGeneration(generation);

                memberGenerations.forEach(memberGeneration -> {
                    Long latestScore = scores.getOrDefault(memberGeneration, 0L);
                    DanggnNotificationMemberRecord record = danggnNotificationRecordService.findMemberRecordOrSave(memberGeneration, currentDanggnRankingRoundId);

                    if (DanggnNotificationSentUnit.MEMBER_RECORD.isNotThreshold(record.getLastNotificationSentScore(), latestScore)) {
                        return;
                    }

                    record.updateLastNotificationSentScore(latestScore);
                    pushNotiEventPublisher.publishPushNotiSendEvent(
                        new DanggnNotificationMemberRecordUpdatedVo(
                            DanggnNotificationSentUnit.MEMBER_RECORD.calculateStage(latestScore),
                            record.getMemberGeneration().getMember(),
                            memberService.getAllDanggnPushNotiTargetableMembers()));
                });
            }
        );
    }

    @Scheduled(initialDelay = 0, fixedDelay = 60 * 1000)
    @Transactional
    public void sendDanggnPlatformRecordPushNoti() {
        // 현재 활동하는 기수 조회
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());

        generations.forEach(
            generation -> {
                Integer generationNumber = generation.getNumber();
                Long currentDanggnRankingRoundId = danggnRankingRoundService.findCurrentByGeneration(generationNumber).getId();
                Map<Platform, Long> scores = danggnScoreService.getDanggnScorePlatformOrderedList(generationNumber, currentDanggnRankingRoundId)
                    .stream()
                    .collect(Collectors.toMap(DanggnScoreRepositoryCustomImpl.DanggnScorePlatformQueryResult::getPlatform, DanggnScoreRepositoryCustomImpl.DanggnScorePlatformQueryResult::getTotalScore));

                Arrays.stream(Platform.values())
                    .forEach(platform -> {
                        Long latestScore = scores.getOrDefault(platform, 0L);
                        DanggnNotificationPlatformRecord record = danggnNotificationRecordService.findPlatformRecordOrSave(generation, platform, currentDanggnRankingRoundId);

                        if (DanggnNotificationSentUnit.PLATFORM_RECORD.isNotThreshold(record.getLastNotificationSentScore(), latestScore)) {
                            return;
                        }

                        record.updateLastNotificationSentScore(latestScore);
                        pushNotiEventPublisher.publishPushNotiSendEvent(
                            new DanggnNotificationPlatformRecordUpdatedVo(
                                DanggnNotificationSentUnit.PLATFORM_RECORD.calculateStage(latestScore),
                                platform,
                                memberService.getAllDanggnPushNotiTargetableMembers()));
                    });
            }
        );
    }
}
