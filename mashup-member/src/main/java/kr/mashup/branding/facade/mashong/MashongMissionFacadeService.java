package kr.mashup.branding.facade.mashong;

import kr.mashup.branding.domain.mashong.MashongMission;
import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.domain.mashong.MashongMissionLog;
import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.service.mashong.*;
import kr.mashup.branding.service.mashong.dto.MissionStatus;
import kr.mashup.branding.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        return MissionStatus.of(mashongMissionLevel, mashongMissionLog);
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
