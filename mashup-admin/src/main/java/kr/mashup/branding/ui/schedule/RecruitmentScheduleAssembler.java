package kr.mashup.branding.ui.schedule;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.schedule.RecruitmentSchedule;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleUpdateVo;

@Component
public class RecruitmentScheduleAssembler {
    RecruitmentScheduleCreateVo toCreateRecruitmentScheduleVo(RecruitmentScheduleCreateRequest request) {
        return RecruitmentScheduleCreateVo.of(
            request.getEventName(),
            request.getEventOccurredAt()
        );
    }

    RecruitmentScheduleUpdateVo toUpdateRecruitmentScheduleVo(RecruitmentScheduleUpdateRequest request) {
        return RecruitmentScheduleUpdateVo.of(
            request.getEventOccurredAt()
        );
    }

    RecruitmentScheduleResponse toRecruitmentScheduleResponse(RecruitmentSchedule recruitmentSchedule) {
        return new RecruitmentScheduleResponse(
            recruitmentSchedule.getRecruitmentScheduleId(),
            recruitmentSchedule.getEventName(),
            recruitmentSchedule.getEventOccurredAt(),
            recruitmentSchedule.getCreatedBy(),
            recruitmentSchedule.getCreatedAt(),
            recruitmentSchedule.getUpdatedBy(),
            recruitmentSchedule.getUpdatedAt()
        );
    }
}
