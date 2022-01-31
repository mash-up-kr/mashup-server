package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;

import java.util.List;

public interface ToastSmsService {
    ToastSmsResponse send(SmsRequestGroup smsRequestGroup, List<SmsRequest> requests);
}
