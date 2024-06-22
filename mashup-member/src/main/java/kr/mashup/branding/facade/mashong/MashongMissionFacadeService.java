package kr.mashup.branding.facade.mashong;

import kr.mashup.branding.domain.mashong.*;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.service.mashong.MashongMissionLevelService;
import kr.mashup.branding.service.mashong.MashongMissionLogService;
import kr.mashup.branding.service.mashong.MashongMissionService;
import kr.mashup.branding.service.mashong.MashongMissionTeamLogService;
import kr.mashup.branding.service.mashong.dto.MissionStatus;
import kr.mashup.branding.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MashongMissionFacadeService {
    private final MashongMissionService mashongMissionService;
    private final MashongMissionLogService mashongMissionLogService;
    private final MashongMissionTeamLogService mashongMissionTeamLogService;
    private final MashongMissionLevelService mashongMissionLevelService;
    private final MemberService memberService;


    @Transactional
    public void apply(MissionStrategyType missionStrategyType, MemberGeneration memberGeneration, Double value) {
        MashongMission mission = mashongMissionService.findMissionByStrategyType(missionStrategyType);
        if (mission.getMissionType() == MissionType.INDIVIDUAL) {
            MashongMissionLevel latestMissionLevel = getLatestMissionLevel(memberGeneration, mission);
            MashongMissionLog mashongMissionLog = mashongMissionLogService.getMissionLog(latestMissionLevel, memberGeneration.getId());
            mashongMissionLog.incrementCurrentStatus(value);
        } else {
            MashongMissionLevel latestMissionLevel = getLatestMissionLevel(memberGeneration.getPlatform(), memberGeneration.getGeneration().getId(), mission);
            MashongMissionTeamLog mashongMissionLog = mashongMissionTeamLogService.getMissionLog(latestMissionLevel, memberGeneration.getPlatform(), memberGeneration.getGeneration().getId());
            mashongMissionLog.incrementCurrentStatus(value);
        }
    }

    @Transactional
    public void setToValue(MissionStrategyType missionStrategyType, MemberGeneration memberGeneration, Double value) {
        MashongMission mission = mashongMissionService.findMissionByStrategyType(missionStrategyType);
        if (mission.getMissionType() == MissionType.INDIVIDUAL) {
            MashongMissionLevel latestMissionLevel = getLatestMissionLevel(memberGeneration, mission);
            MashongMissionLog mashongMissionLog = mashongMissionLogService.getMissionLog(latestMissionLevel, memberGeneration.getId());
            mashongMissionLog.setCurrentStatus(value);
        } else {
            MashongMissionLevel latestMissionLevel = getLatestMissionLevel(memberGeneration.getPlatform(), memberGeneration.getGeneration().getId(), mission);
            MashongMissionTeamLog mashongMissionLog = mashongMissionTeamLogService.getMissionLog(latestMissionLevel, memberGeneration.getPlatform(), memberGeneration.getGeneration().getId());
            mashongMissionLog.setCurrentStatus(value);
        }
    }

    @Transactional
    public MissionStatus missionStatus(Long memberGenerationId, MashongMission mashongMission) {
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);

        if (mashongMission.getMissionType() == MissionType.TEAM) {
            return missionStatus(memberGeneration.getPlatform(), memberGeneration.getGeneration().getId(), mashongMission);
        } else {
            return missionStatus(memberGeneration, mashongMission);
        }
    }

    @Transactional
    public List<MissionStatus> missionStatusList(Long memberGenerationId) {
        List<MashongMission> mashongMissionList = mashongMissionService.findAll();
        return mashongMissionList.stream().map(mission -> missionStatus(memberGenerationId, mission))
            .collect(Collectors.toList());
    }

    private MashongMissionLevel getLatestMissionLevel(MemberGeneration memberGeneration, MashongMission mashongMission) {
        Optional<MashongMissionLog> latestMissionLog = mashongMissionLogService.getLastAchievedMissionLog(mashongMission, memberGeneration.getId());
        if (latestMissionLog.isEmpty()) {
            return mashongMission.getFirstMissionLevel();
        } else if (latestMissionLog.get().getIsCompensated()) {
            MashongMissionLevel mashongMissionLevel = mashongMissionLevelService.findMissionLevel(latestMissionLog.get().getMissionLevelId());
            return mashongMissionLevel.getMashongMission().getNextMissionLevel(mashongMissionLevel.getLevel());
        } else {
            return mashongMissionLevelService.findMissionLevel(latestMissionLog.get().getMissionLevelId());
        }
    }

    private MashongMissionLevel getLatestMissionLevel(Platform platform, Long generationId, MashongMission mashongMission) {
        Optional<MashongMissionTeamLog> latestMissionLog = mashongMissionTeamLogService.getLastAchievedMissionLog(mashongMission, platform, generationId);
        if (latestMissionLog.isEmpty()) {
            return mashongMission.getFirstMissionLevel();
        } else if (latestMissionLog.get().getIsCompensated()) {
            MashongMissionLevel mashongMissionLevel = mashongMissionLevelService.findMissionLevel(latestMissionLog.get().getMissionLevelId());
            return mashongMissionLevel.getMashongMission().getNextMissionLevel(mashongMissionLevel.getLevel());
        } else {
            return mashongMissionLevelService.findMissionLevel(latestMissionLog.get().getMissionLevelId());
        }
    }

    private MissionStatus missionStatus(MemberGeneration memberGeneration, MashongMission mashongMission) {
        MashongMissionLevel latestMissionLevel = getLatestMissionLevel(memberGeneration, mashongMission);
        MashongMissionLog mashongMissionLog = mashongMissionLogService.getMissionLog(latestMissionLevel, memberGeneration.getId());
        return MissionStatus.of(mashongMission, latestMissionLevel, mashongMissionLog);
    }

    private MissionStatus missionStatus(Platform platform, Long generationId, MashongMission mashongMission) {
        MashongMissionLevel latestMissionLevel = getLatestMissionLevel(platform, generationId, mashongMission);
        MashongMissionTeamLog mashongMissionLog = mashongMissionTeamLogService.getMissionLog(latestMissionLevel, platform, generationId);
        return MissionStatus.of(mashongMission, latestMissionLevel, mashongMissionLog);
    }
}
