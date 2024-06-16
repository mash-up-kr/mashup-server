package kr.mashup.branding.facade.mashong;

import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.domain.mashong.MashongMissionLog;
import kr.mashup.branding.domain.mashong.MashongPopcorn;
import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.service.mashong.MashongAttendanceService;
import kr.mashup.branding.service.mashong.MashongMissionLevelService;
import kr.mashup.branding.service.mashong.MashongMissionLogService;
import kr.mashup.branding.service.mashong.MashongPopcornService;
import kr.mashup.branding.service.member.MemberService;
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
    private final MashongMissionLevelService mashongMissionLevelService;
    private final MemberService memberService;

    @Transactional
    public Boolean attend(Long memberGenerationId) {
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        Boolean result = mashongAttendanceService.attend(memberGeneration);
        if (result) {
            mashongMissionFacadeService.apply(MissionStrategyType.MASHONG_ATTENDANCE_MISSION, memberGeneration, 1L);
        }
        return result;
    }

    @Transactional
    public Boolean popcorn(Long memberGenerationId, Long missionLevelId) {
        MashongMissionLevel mashongMissionLevel = mashongMissionLevelService.findMissionLevel(missionLevelId);
        MashongPopcorn mashongPopcorn = mashongPopcornService.findByMemberGenerationId(memberGenerationId);
        MashongMissionLog mashongMissionLog = mashongMissionLogService.getMissionLog(mashongMissionLevel, memberGenerationId);

        if (isCompensatable(mashongMissionLog, mashongMissionLevel)) {
            mashongPopcornService.increasePopcorn(mashongPopcorn.getId(), mashongMissionLevel.getCompensationValue());
            mashongMissionLog.compensated();
            return true;
        } else {
            return false;
        }
    }

    private Boolean isCompensatable(MashongMissionLog mashongMissionLog, MashongMissionLevel mashongMissionLevel) {
        return !mashongMissionLog.getIsCompensated() && (mashongMissionLog.getCurrentStatus() >= mashongMissionLevel.getMissionGoalValue());
    }
}
