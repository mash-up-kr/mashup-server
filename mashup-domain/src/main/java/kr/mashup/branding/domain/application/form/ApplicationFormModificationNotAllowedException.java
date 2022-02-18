package kr.mashup.branding.domain.application.form;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ApplicationFormModificationNotAllowedException extends BadRequestException {
    public ApplicationFormModificationNotAllowedException(String message) {
        super(ResultCode.APPLICATION_FORM_MODIFICATION_NOT_ALLOWED, message);
    }
}
