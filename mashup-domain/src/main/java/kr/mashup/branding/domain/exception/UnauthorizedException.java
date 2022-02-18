package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public class UnauthorizedException extends MashupServerException {
    public UnauthorizedException() {
        super(ResultCode.UNAUTHORIZED);
    }

    public UnauthorizedException(ResultCode resultCode) {
        super(resultCode);
    }
}
