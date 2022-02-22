package kr.mashup.branding.domain.application.form;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ApplicationFormNameDuplicatedException extends BadRequestException {

    public ApplicationFormNameDuplicatedException() {
        super(ResultCode.APPLICATION_FORM_NAME_DUPLICATED);
    }
}
