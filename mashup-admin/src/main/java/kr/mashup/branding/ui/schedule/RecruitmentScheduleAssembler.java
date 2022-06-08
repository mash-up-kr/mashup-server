package kr.mashup.branding.ui.schedule;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleUpdateVo;

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
