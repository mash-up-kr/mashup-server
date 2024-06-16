package kr.mashup.branding.facade.mashong;

import kr.mashup.branding.domain.mashong.*;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.service.mashong.MashongMissionLevelService;
import kr.mashup.branding.service.mashong.MashongMissionLogService;
import kr.mashup.branding.service.mashong.MashongMissionService;
import kr.mashup.branding.service.mashong.dto.MissionStatus;
import kr.mashup.branding.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class MashongMissionFacadeService {
    private final MashongMissionService mashongMissionService;
    private final MashongMissionLogService mashongMissionLogService;
    private final MashongMissionLevelService mashongMissionLevelService;
    private final MemberService memberService;


    @Transactional
    public void apply(MissionStrategyType missionStrategyType, MemberGeneration memberGeneration, Long value) {
        MashongMission mission = mashongMissionService.findMissionByStrategyType(missionStrategyType);
        MashongMissionLevel latestMissionLevel = getLatestMissionLevel(memberGeneration, mission);
        MashongMissionLog mashongMissionLog = mashongMissionLogService.getMissionLog(latestMissionLevel, memberGeneration.getId());
        mashongMissionLog.incrementCurrentStatus(value);
    }

    public MissionStatus missionStatus(Long memberGenerationId, Long missionId) {
        MashongMission mashongMission = mashongMissionService.findMission(missionId);
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        MashongMissionLevel mashongMissionLevel = getLatestMissionLevel(memberGeneration, mashongMission);
        MashongMissionLog mashongMissionLog = mashongMissionLogService.getMissionLog(mashongMissionLevel, memberGenerationId);
        return MissionStatus.of(mashongMission, mashongMissionLevel, mashongMissionLog);
    }

    public Map<MissionType, List<MissionStatus>> missionStatusList(Long memberGenerationId) {
        List<MashongMission> mashongMissionList = mashongMissionService.findAll();
        return mashongMissionList.stream().map(mission -> missionStatus(memberGenerationId, mission.getId()))
            .collect(groupingBy(MissionStatus::getMissionType));
    }

    private MashongMissionLevel getLatestMissionLevel(MemberGeneration memberGeneration, MashongMission mashongMission) {
        Optional<MashongMissionLog> latestMissionLog = mashongMissionLogService.getLastAchievedMissionLog(mashongMission.getId(), memberGeneration.getId());
        if (latestMissionLog.isEmpty()) {
            return mashongMissionLevelService.getFirstMissionLevel(mashongMission.getId());
        } else if (latestMissionLog.get().getIsCompensated()) {
            return mashongMissionLevelService.findNextMissionLevel(latestMissionLog.get().getMissionLevelId());
        } else {
            return mashongMissionLevelService.findMissionLevel(latestMissionLog.get().getMissionLevelId());
        }
    }
}
