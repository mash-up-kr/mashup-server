package kr.mashup.branding.domain.recruitmentschedule;

import java.time.LocalDateTime;

import lombok.Value;

@Value(staticConstructor = "of")
public class RecruitmentScheduleCreateVo {
    String eventName;
    LocalDateTime eventOccurredAt;
}
