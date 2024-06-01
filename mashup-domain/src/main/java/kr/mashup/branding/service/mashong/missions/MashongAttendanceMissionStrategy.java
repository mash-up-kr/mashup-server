package kr.mashup.branding.service.mashong.missions;

import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MashongAttendanceMissionStrategy implements MissionStrategy {
    private MashongMissionLevel mashongMissionLevel;

    @Override
    public boolean isAchieved(MemberGeneration memberGeneration) {
        return false;
    }

    @Override
    public String getTitle() {
        return mashongMissionLevel.getTitle();
    }

    @Override
    public String getDescription() {
        return "팝콘 " + mashongMissionLevel.getCompensationValue().toString() + "알";
    }
}
