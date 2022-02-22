package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ApplicationAlreadySubmittedException extends BadRequestException {
    public ApplicationAlreadySubmittedException() {
        super(ResultCode.APPLICATION_ALREADY_SUBMITTED);
    }

    public ApplicationAlreadySubmittedException(String message) {
        super(ResultCode.APPLICATION_ALREADY_SUBMITTED, message);
    }
}
