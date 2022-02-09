package kr.mashup.branding.ui.schedule;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.schedule.RecruitmentEvent;
import kr.mashup.branding.domain.schedule.RecruitmentSchedule;

@Component
public class RecruitmentScheduleAssembler {
    RecruitmentEvent toRecruitmentEvent(RecruitmentScheduleRequest recruitmentScheduleRequest) {
        return RecruitmentEvent.of(
            recruitmentScheduleRequest.getEventName(),
            recruitmentScheduleRequest.getEventOccurredAt()
        );
    }

    RecruitmentScheduleResponse toRecruitmentScheduleResponse(RecruitmentSchedule recruitmentSchedule) {
        return new RecruitmentScheduleResponse(
            recruitmentSchedule.getRecruitmentScheduleId(),
            recruitmentSchedule.getEventName(),
            recruitmentSchedule.getEventOccurredAt(),
            recruitmentSchedule.getCreatedAt(),
            recruitmentSchedule.getUpdatedAt()
        );
    }
}
