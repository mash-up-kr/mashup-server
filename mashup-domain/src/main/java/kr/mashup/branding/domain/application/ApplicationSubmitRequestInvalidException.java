package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ApplicationSubmitRequestInvalidException extends BadRequestException {
    public ApplicationSubmitRequestInvalidException(String message) {
        super(ResultCode.APPLICATION_SUBMIT_REQUEST_INVALID, message);
    }

    public ApplicationSubmitRequestInvalidException(String message, Throwable cause) {
        super(ResultCode.APPLICATION_SUBMIT_REQUEST_INVALID, message, cause);
    }
}
