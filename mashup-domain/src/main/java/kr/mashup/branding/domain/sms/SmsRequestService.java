package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;

import java.util.List;

interface SmsRequestService {

    void saveAll(List<SmsRequest> requests);
    SmsRequest createSmsRequest(SmsRequestVo smsRequestVo);
    List<SmsRequest> getRequests(Long groupId);
    List<SmsRequest> getFailedRequests(Long groupId);
    void markRequests(ToastSmsResponse toastSmsResponse, List<SmsRequest> savedRequests);
}
