package kr.mashup.branding.facade.mashong;

import kr.mashup.branding.domain.mashong.MashongMission;
import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.domain.mashong.MashongMissionLog;
import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.service.mashong.*;
import kr.mashup.branding.service.mashong.dto.MissionStatus;
import kr.mashup.branding.service.mashong.missions.MissionStrategy;
import kr.mashup.branding.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MashongFacadeService {
    private final MashongAttendanceService mashongAttendanceService;
    private final MashongMissionManager mashongMissionManager;
    private final MashongMissionService mashongMissionService;
    private final MashongMissionLogService mashongMissionLogService;
    private final MashongMissionLevelService mashongMissionLevelService;
    private final MemberService memberService;

    public Boolean attend(Long memberGenerationId) {
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        Boolean result = mashongAttendanceService.attend(memberGeneration);

        checkMission(MissionStrategyType.MASHONG_ATTENDANCE_MISSION, memberGeneration);
        return result;
    }

    public void checkMission(MissionStrategyType missionStrategyType, MemberGeneration memberGeneration) {
        MissionStrategy missionStrategy = mashongMissionManager.getStrategy(missionStrategyType);
        missionStrategy.checkMission(memberGeneration);
    }

    public MissionStatus missionStatus(Long memberGenerationId, Long missionId) {
        MashongMission mashongMission = mashongMissionService.findMission(missionId);
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        MissionStrategy missionStrategy = mashongMissionManager.getStrategy(mashongMission.getMissionStrategyType());
        return missionStrategy.getMissionStatus(getLatestMissionLevel(memberGeneration, mashongMission), memberGeneration);
    }

    public MashongMissionLevel getLatestMissionLevel(MemberGeneration memberGeneration, MashongMission mashongMission) {
        Optional<MashongMissionLog> latestMissionLog = mashongMissionLogService.getLastAchievedMissionLog(mashongMission.getId(), memberGeneration.getId());
        if (latestMissionLog.isPresent()) {
            return mashongMissionLevelService.findMissionLevel(latestMissionLog.get().getMissionLevelId());
        } else {
            return mashongMissionLevelService.getFirstMissionLevel(mashongMission.getId());
        }
    }
}
