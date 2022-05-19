package kr.mashup.branding.domain.application.form;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ApplicationFormDeleteFailedException extends BadRequestException {
    public ApplicationFormDeleteFailedException() {
        super(ResultCode.APPLICATION_FORM_DELETE_NOT_ALLOWED);
    }
}
