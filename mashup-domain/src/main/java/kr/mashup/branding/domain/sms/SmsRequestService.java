package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;

import java.util.List;

public interface SmsRequestService {
    List<SmsRequest> createAndSaveAll(SmsRequestGroup smsRequestGroup, List<SmsRequestVo> smsRequestVoList);
    List<SmsRequest> getRequests(Long groupId);
    List<SmsRequest> getFailedRequests(Long groupId);
    void markRequestsWithToastResponse(ToastSmsResponse toastSmsResponse, SmsRequestGroup smsRequestGroup);
}
