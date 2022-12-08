package kr.mashup.branding.ui.recruitmentschedule;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.recruitmentschedule.vo.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RecruitmentScheduleCreateRequest {
    private RecruitmentScheduleEventName eventName;
    private LocalDateTime eventOccurredAt;

    public RecruitmentScheduleCreateVo toVo(){
        return RecruitmentScheduleCreateVo.of(eventName, eventOccurredAt);
    }
}
