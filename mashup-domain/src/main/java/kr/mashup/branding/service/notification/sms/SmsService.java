package kr.mashup.branding.service.notification.sms;

import kr.mashup.branding.domain.notification.sms.vo.SmsRequestVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendResultVo;

public interface SmsService {
    SmsSendResultVo send(SmsRequestVo smsRequestVo);
}
