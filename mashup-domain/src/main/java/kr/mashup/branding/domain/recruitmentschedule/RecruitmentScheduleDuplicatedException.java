package kr.mashup.branding.domain.recruitmentschedule;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class RecruitmentScheduleDuplicatedException extends BadRequestException {
    public RecruitmentScheduleDuplicatedException(String message) {
        super(ResultCode.RECRUITMENT_SCHEDULE_DUPLICATED, message);
    }
}
