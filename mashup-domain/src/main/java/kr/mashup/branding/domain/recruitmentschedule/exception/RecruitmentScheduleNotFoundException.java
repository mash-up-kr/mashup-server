package kr.mashup.branding.domain.recruitmentschedule.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class RecruitmentScheduleNotFoundException extends NotFoundException {
    public RecruitmentScheduleNotFoundException() {
        super(ResultCode.RECRUITMENT_SCHEDULE_NOT_FOUND);
    }
}
