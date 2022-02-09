package kr.mashup.branding.ui.schedule;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecruitmentScheduleResponse {
    private final Long recruitmentScheduleId;
    private final String eventName;
    private final LocalDateTime eventOccurredAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
