package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsRequest;
import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;

interface ToastSmsService {
    ToastSmsResponse send(ToastSmsRequest toastSmsRequest);
}
