package kr.mashup.branding.ui.recruitmentschedule;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
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

    public static RecruitmentScheduleResponse from(RecruitmentSchedule recruitmentSchedule){
        return new RecruitmentScheduleResponse(
            recruitmentSchedule.getRecruitmentScheduleId(),
            recruitmentSchedule.getEventName().name(),
            recruitmentSchedule.getEventOccurredAt(),
            recruitmentSchedule.getCreatedBy(),
            recruitmentSchedule.getCreatedAt(),
            recruitmentSchedule.getUpdatedBy(),
            recruitmentSchedule.getUpdatedAt()
        );
    }

}
