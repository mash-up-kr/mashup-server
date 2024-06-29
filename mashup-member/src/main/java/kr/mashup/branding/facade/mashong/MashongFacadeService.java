package kr.mashup.branding.facade.mashong;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.mashong.*;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.service.mashong.*;
import kr.mashup.branding.service.mashong.dto.LevelUpResult;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.mashong.response.MashongFeedResponse;
import kr.mashup.branding.ui.mashong.response.MashongLevelUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MashongFacadeService {

    private final MashongAttendanceService mashongAttendanceService;
    private final MashongMissionFacadeService mashongMissionFacadeService;
    private final MashongPopcornService mashongPopcornService;
    private final MashongMissionLogService mashongMissionLogService;
    private final MashongMissionTeamLogService mashongMissionTeamLogService;
    private final MashongMissionLevelService mashongMissionLevelService;
    private final PlatformMashongService platformMashongService;
    private final PlatformMashongLevelService platformMashongLevelService;
    private final MemberService memberService;

    @Transactional
    public Boolean attend(Long memberGenerationId) {
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        Boolean result = mashongAttendanceService.attend(memberGeneration);
        if (result) {
            mashongMissionFacadeService.apply(MissionStrategyType.MASHONG_ATTENDANCE_INDIVIDUAL, memberGeneration, 1.0);
            mashongMissionFacadeService.setToValue(MissionStrategyType.MASHONG_ATTENDANCE_TEAN, memberGeneration, getPlatformAttendStatus(memberGeneration.getPlatform(), memberGeneration.getGeneration()));
            mashongPopcornService.givePopcorn(memberGenerationId, 1L);
        }
        return result;
    }

    @Transactional
    public Boolean compensatePopcorn(Long memberGenerationId, Long missionLevelId) {
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        MashongMissionLevel mashongMissionLevel = mashongMissionLevelService.findMissionLevel(missionLevelId);
        MashongPopcorn mashongPopcorn = mashongPopcornService.findByMemberGenerationId(memberGenerationId);
        switch (mashongMissionLevel.getMashongMission().getMissionType()) {
            case INDIVIDUAL:
                MashongMissionLog mashongMissionLog = mashongMissionLogService.getMissionLog(mashongMissionLevel, memberGenerationId);

                if (isCompensatable(mashongMissionLog, mashongMissionLevel)) {
                    mashongPopcornService.increasePopcorn(mashongPopcorn.getId(), mashongMissionLevel.getCompensationValue());
                    mashongMissionLog.compensated();
                    return true;
                } else {
                    return false;
                }
            case TEAM:
                MashongMissionTeamLog mashongMissionTeamLog = mashongMissionTeamLogService.getMissionLog(mashongMissionLevel, memberGeneration.getPlatform(), memberGeneration.getGeneration().getId());

                if (isCompensatable(mashongMissionTeamLog, mashongMissionLevel)) {
                    mashongPopcornService.increasePopcorn(mashongPopcorn.getId(), mashongMissionLevel.getCompensationValue());
                    mashongMissionTeamLog.compensated();
                    return true;
                } else {
                    return false;
                }
        }
        return false;
    }

    public Long getPopcornCount(Long memberGenerationId) {
        return mashongPopcornService.findByMemberGenerationId(memberGenerationId).getPopcorn();
    }

    private Double getPlatformAttendStatus(Platform platform, Generation generation) {
        int totalMemberCount = memberService.getAllByPlatformAndGeneration(platform, generation).size();
        Long distinctAttendMemberCount = mashongAttendanceService.distinctPlatformMemberAttendanceCount(platform);
        return distinctAttendMemberCount.doubleValue() / totalMemberCount;
    }

    private Boolean isCompensatable(MashongMissionLog mashongMissionLog, MashongMissionLevel mashongMissionLevel) {
        return !mashongMissionLog.getIsCompensated() && (mashongMissionLog.getCurrentStatus() >= mashongMissionLevel.getMissionGoalValue());
    }

    private Boolean isCompensatable(MashongMissionTeamLog mashongMissionLog, MashongMissionLevel mashongMissionLevel) {
        return !mashongMissionLog.getIsCompensated() && (mashongMissionLog.getCurrentStatus() >= mashongMissionLevel.getMissionGoalValue());
    }

    @Transactional
    public MashongFeedResponse feedPopcorn(Long memberGenerationId, Long popcornCount) {
        final MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        final Platform platform = memberService.getLatestPlatform(memberGeneration.getMember());
        final Generation generation = memberGeneration.getGeneration();

        final PlatformMashong platformMashong = platformMashongService.findByPlatformAndGeneration(platform, generation);
        if (platformMashong.isMaxLevel()) {
            final MashongPopcorn mashongPopcorn = mashongPopcornService.findByMemberGenerationId(memberGenerationId);
            return MashongFeedResponse.of(false, platformMashong, mashongPopcorn);
        }

        platformMashongService.feedPopcorn(platformMashong, popcornCount);
        final MashongPopcorn mashongPopcorn = mashongPopcornService.decreasePopcorn(memberGenerationId, popcornCount);

        // TODO: event publisher 로 변경
        mashongMissionFacadeService.apply(
                MissionStrategyType.MASHONG_POPCORN_INDIVIDUAL,
                memberGeneration,
                popcornCount.doubleValue()
        );

        return MashongFeedResponse.of(true, platformMashong, mashongPopcorn);
    }

    @Transactional
    public MashongLevelUpResponse levelUp(Long memberGenerationId, int goalLevel) {
        PlatformMashongLevel goalPlatformMashongLevel = platformMashongLevelService.findByLevel(goalLevel);
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        Platform platform = memberService.getLatestPlatform(memberGeneration.getMember());
        Generation generation = memberGeneration.getGeneration();

        LevelUpResult levelUpResult = platformMashongService.levelUp(platform, generation, goalPlatformMashongLevel);
        System.out.println(levelUpResult.toString());
        if (!levelUpResult.isLevelUpResult()) {
            PlatformMashong platformMashong = platformMashongService.findByPlatformAndGeneration(platform, generation);
            return MashongLevelUpResponse.of(levelUpResult, platformMashong.getLevel());
        }

        // TODO: even publisher 로 변경
        if (levelUpResult.isUpdateLog()) {
            mashongMissionFacadeService.setToValue(
                    MissionStrategyType.MASHONG_LEVEL_TEAM,
                    memberGeneration,
                    (double) goalPlatformMashongLevel.getLevel()
            );
        }

        return MashongLevelUpResponse.of(levelUpResult, goalPlatformMashongLevel);
    }
}
