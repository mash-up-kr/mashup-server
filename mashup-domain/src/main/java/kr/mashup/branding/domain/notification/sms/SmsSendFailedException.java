package kr.mashup.branding.domain.notification.sms;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.ServiceUnavailableException;

public class SmsSendFailedException extends ServiceUnavailableException {
    public SmsSendFailedException() {
        super(ResultCode.NOTIFICATION_FAILED_TO_SEND_SMS);
    }
}
