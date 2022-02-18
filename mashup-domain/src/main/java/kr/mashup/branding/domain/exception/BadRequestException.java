package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public class BadRequestException extends MashupServerException {
    public BadRequestException() {
        super(ResultCode.BAD_REQUEST);
    }

    public BadRequestException(ResultCode resultCode) {
        super(resultCode);
    }
}
