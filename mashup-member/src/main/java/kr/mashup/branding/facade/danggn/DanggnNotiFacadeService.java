package kr.mashup.branding.facade.danggn;

import static kr.mashup.branding.repository.danggn.DanggnScoreRepositoryCustomImpl.*;

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
import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.pushnoti.vo.DanggnFirstRecordMemberUpdatedVo;
import kr.mashup.branding.domain.pushnoti.vo.DanggnFirstRecordPlatformUpdatedVo;
import kr.mashup.branding.domain.pushnoti.vo.DanggnNotificationMemberRecordUpdatedVo;
import kr.mashup.branding.domain.pushnoti.vo.DanggnNotificationPlatformRecordUpdatedVo;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.danggn.DanggnCacheKey;
import kr.mashup.branding.service.danggn.DanggnCacheService;
import kr.mashup.branding.service.danggn.DanggnNotificationRecordService;
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

    private static final Long DANGGN_NOTIFICATION_MEMBER_RECORD_UNIT = 10_000L;

    private static final Long DANGGN_NOTIFICATION_PLATFORM_RECORD_UNIT = 100_000L;

    @Scheduled(cron = "0 0 09,13,19 * * *")
    @Transactional(readOnly = true)
    public void sendDanggnFirstRecordMemberUpdatedPushNoti() {
        // 현재 활동하는 기수 조회
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());
        if (generations.isEmpty())
            return;

        generations.forEach(
                generation -> {
                    Integer generationNumber = generation.getNumber();
                    String currentFirstRecordMemberId = danggnCacheService.findFirstRecordMemberId(generationNumber);
                    String cachedFirstRecordMemberId = danggnCacheService.getCachedFirstRecordMemberId(generationNumber);

                    if (currentFirstRecordMemberId == null || currentFirstRecordMemberId.equals(cachedFirstRecordMemberId)) {
                        return;
                    }
                    // 변경된 부분 있으면 업데이트 푸시 알림 보낸 후 캐시 업데이트
                    pushNotiEventPublisher.publishPushNotiSendEvent(new DanggnFirstRecordMemberUpdatedVo(memberService.getAllDanggnPushNotiTargetableMembers()));
                    danggnCacheService.updateCachedFirstRecord(DanggnCacheKey.MEMBER, generationNumber, currentFirstRecordMemberId);
                }
        );
    }

    @Scheduled(cron = "0 0 09,13,19 * * *")
    @Transactional(readOnly = true)
    public void sendDanggnFirstRecordPlatformPushNoti() {
        // 현재 활동하는 기수 조회
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());
        if (generations.isEmpty())
            return;

        generations.forEach(
                generation -> {
                    Integer generationNumber = generation.getNumber();
                    String currentFirstRecordPlatform = danggnCacheService.findFirstRecordPlatform(generationNumber);
                    String cachedFirstRecordPlatform = danggnCacheService.getCachedFirstRecordPlatform(generationNumber);

                    if (currentFirstRecordPlatform == null || currentFirstRecordPlatform.equals(cachedFirstRecordPlatform)) {
                        return;
                    }
                    // 변경된 부분 있으면  업데이트 푸시 알림 보낸 후 캐시 업데이트
                    pushNotiEventPublisher.publishPushNotiSendEvent(new DanggnFirstRecordPlatformUpdatedVo(memberService.getAllDanggnPushNotiTargetableMembers()));
                    danggnCacheService.updateCachedFirstRecord(DanggnCacheKey.PLATFORM, generationNumber, currentFirstRecordPlatform);
                }
        );
    }

    @Scheduled(initialDelay = 0, fixedDelay = 6000)
    @Transactional
    public void sendDanggnPlatformRecordPushNoti() {
        // 현재 활동하는 기수 조회
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());
        if (generations.isEmpty())
            return;

        generations.forEach(
            generation -> {
                Integer generationNumber = generation.getNumber();
                Map<Platform, Long> results = danggnScoreService.getDanggnScorePlatformOrderedList(generationNumber)
                    .stream()
                    .collect(Collectors.toMap(DanggnScorePlatformQueryResult::getPlatform, DanggnScorePlatformQueryResult::getTotalScore));

                Arrays.stream(Platform.values())
                    .forEach(platform -> {
                        Long latestScore = results.getOrDefault(platform, 0L);
                        DanggnNotificationPlatformRecord platformRecord = danggnNotificationRecordService.findDanggnNotificationPlatformRecordByPlatform(generation, platform);

                        if (!danggnNotificationRecordService.isUpdatedUnit(latestScore,
                            platformRecord.getLastNotificationSentScore(),
                            DANGGN_NOTIFICATION_PLATFORM_RECORD_UNIT)) {
                            return;
                        }

                        platformRecord.updateLastNotificationSentScore(latestScore);
                        pushNotiEventPublisher.publishPushNotiSendEvent(new DanggnNotificationPlatformRecordUpdatedVo(latestScore / DANGGN_NOTIFICATION_PLATFORM_RECORD_UNIT * DANGGN_NOTIFICATION_PLATFORM_RECORD_UNIT, platform, memberService.getAllDanggnPushNotiTargetableMembers()));
                    });
            }
        );
    }

    @Scheduled(initialDelay = 0, fixedDelay = 6000)
    @Transactional
    public void sendDanggnMemberRecordPushNoti() {
        // 현재 활동하는 기수 조회
        List<Generation> generations = generationService.getAllActiveInAt(LocalDate.now());
        if (generations.isEmpty())
            return;

        generations.forEach(
            generation -> {
                Integer generationNumber = generation.getNumber();
                Map<MemberGeneration, Long> results = danggnScoreService.getDanggnScoreOrderedList(generationNumber)
                    .stream()
                    .collect(Collectors.toMap(DanggnScore::getMemberGeneration, DanggnScore::getTotalShakeScore));
                List<MemberGeneration> memberGenerations = memberService.findByGeneration(generation);

                memberGenerations.forEach(memberGeneration -> {
                        Long latestScore = results.getOrDefault(memberGeneration, 0L);
                        DanggnNotificationMemberRecord memberRecord = danggnNotificationRecordService.findDanggnNotificationMemberRecordByMemberGenerationOrSave(memberGeneration);

                        if (!danggnNotificationRecordService.isUpdatedUnit(latestScore,
                            memberRecord.getLastNotificationSentScore(),
                            DANGGN_NOTIFICATION_MEMBER_RECORD_UNIT)) {
                            return;
                        }

                    memberRecord.updateLastNotificationSentScore(latestScore);
                    pushNotiEventPublisher.publishPushNotiSendEvent(
                        new DanggnNotificationMemberRecordUpdatedVo(
                            latestScore / DANGGN_NOTIFICATION_MEMBER_RECORD_UNIT
                                * DANGGN_NOTIFICATION_MEMBER_RECORD_UNIT,
                            memberRecord.getMemberGeneration().getMember(),
                            memberService.getAllDanggnPushNotiTargetableMembers()));
                });
            }
        );
    }
}
