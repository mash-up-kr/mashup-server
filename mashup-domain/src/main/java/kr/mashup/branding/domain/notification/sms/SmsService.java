package kr.mashup.branding.domain.notification.sms;

public interface SmsService {
    SmsSendResultVo send(SmsRequestVo smsRequestVo);
}
