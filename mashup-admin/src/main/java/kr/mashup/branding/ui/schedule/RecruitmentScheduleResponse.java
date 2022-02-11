package kr.mashup.branding.ui.schedule;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@SuppressWarnings("FieldMayBeFinal")
public class RecruitmentScheduleResponse {
    private Long recruitmentScheduleId;
    private String eventName;
    private LocalDateTime eventOccurredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
