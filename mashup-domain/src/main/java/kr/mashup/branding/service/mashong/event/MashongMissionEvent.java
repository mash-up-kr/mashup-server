package kr.mashup.branding.service.mashong.event;


import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.service.mashong.dto.MissionEventType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MashongMissionEvent {

    private final MissionStrategyType missionStrategyType;
    private final MemberGeneration memberGeneration;
    private final Double value;
    private final MissionEventType eventType;

    public boolean isApplyEvent() {
        return eventType.isApplyType();
    }
}
