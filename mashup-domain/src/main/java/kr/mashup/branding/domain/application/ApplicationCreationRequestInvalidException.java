package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ApplicationCreationRequestInvalidException extends BadRequestException {
    public ApplicationCreationRequestInvalidException(String message, Throwable cause) {
        super(ResultCode.APPLICATION_CREATION_REQUEST_INVALID, message, cause);
    }
}
