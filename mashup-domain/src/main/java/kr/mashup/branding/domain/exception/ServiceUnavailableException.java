package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public class ServiceUnavailableException extends MashupServerException {
    protected ServiceUnavailableException(ResultCode resultCode) {
        super(resultCode);
    }
}
