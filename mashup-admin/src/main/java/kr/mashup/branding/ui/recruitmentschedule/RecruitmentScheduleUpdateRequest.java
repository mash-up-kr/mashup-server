package kr.mashup.branding.ui.recruitmentschedule;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleUpdateVo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RecruitmentScheduleUpdateRequest {
    private LocalDateTime eventOccurredAt;

    public RecruitmentScheduleUpdateVo toVo(){
        return RecruitmentScheduleUpdateVo.of(eventOccurredAt);
    }
}
