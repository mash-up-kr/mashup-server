package kr.mashup.branding.domain.sms;

import java.util.List;

interface SmsRequestService {
    SmsRequest createSmsRequest(SmsRequestVo smsRequestVo);
    List<SmsRequest> getRequests(Long groupId);
    List<SmsRequest> getFailedRequests(Long groupId);
    void markAsSuccess(SmsRequest smsRequest);
    void markAsFail(SmsRequest smsRequest);
}
