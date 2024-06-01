package kr.mashup.branding.service.mashong.missions;

import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.service.mashong.dto.MissionStatus;

public interface MissionStrategy {
    MissionStatus getMissionStatus(MashongMissionLevel mashongMissionLevel, MemberGeneration memberGeneration);

    void checkMission(MemberGeneration memberGeneration);

    MissionStrategyType getMissionStrategyType();
}
