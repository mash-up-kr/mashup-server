package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public class InternalServerErrorException extends MashupServerException {
    protected InternalServerErrorException() {
        super(ResultCode.INTERNAL_SERVER_ERROR);
    }

    protected InternalServerErrorException(ResultCode resultCode) {
        super(resultCode);
    }
}
