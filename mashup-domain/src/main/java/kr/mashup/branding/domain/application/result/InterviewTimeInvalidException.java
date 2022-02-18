package kr.mashup.branding.domain.application.result;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class InterviewTimeInvalidException extends BadRequestException {
    public InterviewTimeInvalidException(String message) {
        super(ResultCode.INTERVIEW_TIME_INVALID, message);
    }
}
