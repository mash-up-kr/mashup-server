package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ApplicationModificationNotAllowedException extends BadRequestException {
    public ApplicationModificationNotAllowedException(String message) {
        super(ResultCode.APPLICATION_MODIFICATION_NOT_ALLOWED, message);
    }
}
