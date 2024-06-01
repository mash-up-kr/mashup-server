package kr.mashup.branding.service.mashong.missions;

import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.repository.mashong.MashongAttendanceRepository;
import kr.mashup.branding.service.mashong.dto.MissionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MashongAttendanceMissionStrategy implements MissionStrategy {
    private final MashongAttendanceRepository mashongAttendanceRepository;

    private Boolean isAchieved(MemberGeneration memberGeneration, MashongMissionLevel mashongMissionLevel) {
        // Todo
        return false;
    }

    private String getTitle(MashongMissionLevel mashongMissionLevel) {
        return mashongMissionLevel.getTitle();
    }

    private String getDescription(MashongMissionLevel mashongMissionLevel) {
        return "팝콘 " + mashongMissionLevel.getCompensationValue().toString() + "알";
    }

    @Override
    public MissionStatus getMissionStatus(MashongMissionLevel mashongMissionLevel, MemberGeneration memberGeneration) {
        return MissionStatus.of(
            isAchieved(memberGeneration, mashongMissionLevel),
            0L,
            getTitle(mashongMissionLevel),
            getDescription(mashongMissionLevel)
        );
    }

    @Override
    public void checkMission(MemberGeneration memberGeneration) {

    }

    @Override
    public MissionStrategyType getMissionStrategyType() {
        return MissionStrategyType.MASHONG_ATTENDANCE_MISSION;
    }

}
