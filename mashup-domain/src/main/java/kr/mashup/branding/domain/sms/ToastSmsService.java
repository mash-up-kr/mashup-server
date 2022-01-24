package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;

import java.util.List;

interface ToastSmsService {
    ToastSmsResponse send(List<SmsRequest> requests);
}
