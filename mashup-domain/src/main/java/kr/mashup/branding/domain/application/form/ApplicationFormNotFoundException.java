package kr.mashup.branding.domain.application.form;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class ApplicationFormNotFoundException extends NotFoundException {
    public ApplicationFormNotFoundException() {
        super(ResultCode.APPLICATION_FORM_NOT_FOUND);
    }
}
