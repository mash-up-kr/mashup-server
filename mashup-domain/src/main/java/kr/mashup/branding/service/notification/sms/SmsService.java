package kr.mashup.branding.service.notification.sms;

import kr.mashup.branding.domain.notification.sms.SmsRequestVo;
import kr.mashup.branding.domain.notification.sms.SmsSendResultVo;

public interface SmsService {
    SmsSendResultVo send(SmsRequestVo smsRequestVo);
}
