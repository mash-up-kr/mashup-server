package kr.mashup.branding.domain.danggn.Exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class DanggnTodayMessageNotFoundException extends NotFoundException {
    public DanggnTodayMessageNotFoundException() {
        super(ResultCode.TODAY_MESSAGE_NOT_FOUND);
    }
}
