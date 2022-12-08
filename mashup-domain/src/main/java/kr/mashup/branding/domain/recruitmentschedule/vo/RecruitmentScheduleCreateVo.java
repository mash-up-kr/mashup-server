package kr.mashup.branding.domain.recruitmentschedule.vo;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;
import lombok.Value;

@Value(staticConstructor = "of")
public class RecruitmentScheduleCreateVo {
    RecruitmentScheduleEventName eventName;
    LocalDateTime eventOccurredAt;
}
