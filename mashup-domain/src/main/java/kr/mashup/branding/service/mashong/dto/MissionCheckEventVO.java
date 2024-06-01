package kr.mashup.branding.service.mashong.dto;

import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.Getter;

@Getter
public class MissionCheckEventVO {
    private MissionStrategyType missionStrategyType;
    private MemberGeneration memberGeneration;

    public MissionCheckEventVO(MissionStrategyType missionStrategyType, MemberGeneration memberGeneration) {
        this.missionStrategyType = missionStrategyType;
        this.memberGeneration = memberGeneration;
    }
}
