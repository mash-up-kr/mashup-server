package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;

import java.util.List;

public interface SmsRequestService {
    List<SmsRequest> createAndSaveAll(SmsRequestGroup smsRequestGroup, List<SmsRequestVo> smsRequestVoList);
    List<SmsRequest> getRequests(Long smsRequestGroupId);
    List<SmsRequest> getFailedRequests(Long smsRequestGroupId);
    void markRequestsWithToastResponse(ToastSmsResponse toastSmsResponse, SmsRequestGroup smsRequestGroup);
    void updateSmsSendKey(SmsRequest smsRequest, String smsSendKey);
}
