package kr.mashup.branding.ui.schedule;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@SuppressWarnings("FieldMayBeFinal")
public class RecruitmentScheduleResponse {
    private Long recruitmentScheduleId;
    private String eventName;
    private LocalDateTime eventOccurredAt;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
