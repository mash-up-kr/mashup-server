package kr.mashup.branding.ui.recruitmentschedule;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RecruitmentScheduleCreateRequest {
    private String eventName;
    private LocalDateTime eventOccurredAt;
}
