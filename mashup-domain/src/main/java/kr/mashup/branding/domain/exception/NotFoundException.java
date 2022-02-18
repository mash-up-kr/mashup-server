package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public class NotFoundException extends MashupServerException {
    public NotFoundException() {
        super(ResultCode.NOT_FOUND);
    }

    public NotFoundException(ResultCode resultCode) {
        super(resultCode);
    }
}
