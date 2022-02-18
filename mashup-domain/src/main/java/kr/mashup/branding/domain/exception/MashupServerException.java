package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public abstract class MashupServerException extends RuntimeException {
    private final ResultCode resultCode;

    protected MashupServerException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    protected MashupServerException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    protected MashupServerException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    protected MashupServerException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
