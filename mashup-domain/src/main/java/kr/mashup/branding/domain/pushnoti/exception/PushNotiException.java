package kr.mashup.branding.domain.pushnoti.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.ServiceUnavailableException;

public class PushNotiException extends ServiceUnavailableException {
    public PushNotiException() {
        super(ResultCode.PUSH_NOTI_FAILED_TO_SEND);
    }
}
