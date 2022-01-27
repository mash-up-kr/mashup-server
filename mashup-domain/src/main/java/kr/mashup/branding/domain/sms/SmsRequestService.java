package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;

import java.util.List;

interface SmsRequestService {
    void createAndSaveAll(SmsRequestGroup smsRequestGroup, List<SmsRequestVo> smsRequestVoList);
    List<SmsRequest> getRequests(Long groupId);
    List<SmsRequest> getFailedRequests(Long groupId);
    void markRequests(ToastSmsResponse toastSmsResponse, SmsRequestGroup smsRequestGroup) throws Exception;
}
