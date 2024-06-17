package kr.mashup.branding.facade.mashong;

import kr.mashup.branding.domain.mashong.*;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.service.mashong.*;
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
    private final MashongMissionTeamLogService mashongMissionTeamLogService;
    private final MashongMissionLevelService mashongMissionLevelService;
    private final MemberService memberService;

    @Transactional
    public Boolean attend(Long memberGenerationId) {
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        Boolean result = mashongAttendanceService.attend(memberGeneration);
        if (result) {
            mashongMissionFacadeService.apply(MissionStrategyType.MASHONG_ATTENDANCE_INDIVIDUAL, memberGeneration, 1L);
        }
        return result;
    }

    @Transactional
    public Boolean popcorn(Long memberGenerationId, Long missionLevelId) {
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
                MashongMissionTeamLog mashongMissionTeamLog = mashongMissionTeamLogService.getMissionLog(mashongMissionLevel, memberGeneration.getPlatform());

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

    private Boolean isCompensatable(MashongMissionLog mashongMissionLog, MashongMissionLevel mashongMissionLevel) {
        return !mashongMissionLog.getIsCompensated() && (mashongMissionLog.getCurrentStatus() >= mashongMissionLevel.getMissionGoalValue());
    }

    private Boolean isCompensatable(MashongMissionTeamLog mashongMissionLog, MashongMissionLevel mashongMissionLevel) {
        return !mashongMissionLog.getIsCompensated() && (mashongMissionLog.getCurrentStatus() >= mashongMissionLevel.getMissionGoalValue());
    }
}
