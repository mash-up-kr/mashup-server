package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class ApplicationNotFoundException extends NotFoundException {
    public ApplicationNotFoundException() {
        super(ResultCode.APPLICATION_NOT_FOUND);
    }
}
