package kr.mashup.branding.service.notification.sms;

import kr.mashup.branding.domain.notification.sms.vo.SmsRequestVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendResultVo;

// 외부서비스 호출하는 거라서 인터페이스 유지 필요
public interface SmsService {
    SmsSendResultVo send(SmsRequestVo smsRequestVo);
}
