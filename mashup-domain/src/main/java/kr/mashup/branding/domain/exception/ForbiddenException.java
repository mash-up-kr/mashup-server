package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public class ForbiddenException extends MashupServerException {
    public ForbiddenException() {
        super(ResultCode.FORBIDDEN);
    }

    public ForbiddenException(ResultCode resultCode) {
        super(resultCode);
    }
}
