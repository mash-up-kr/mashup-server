package kr.mashup.branding.domain.application.form;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ApplicationFormAlreadyExistException extends BadRequestException {
    public ApplicationFormAlreadyExistException(String message) {
        super(ResultCode.APPLICATION_FORM_ALREADY_EXIST, message);
    }
}
