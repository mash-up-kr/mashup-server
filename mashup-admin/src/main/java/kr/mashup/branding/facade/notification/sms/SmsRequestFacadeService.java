package kr.mashup.branding.facade.notification.sms;

import java.util.List;

import kr.mashup.branding.domain.notification.sms.SmsRequest;

public interface SmsRequestFacadeService {
    List<SmsRequest> getSmsRequests(Long adminMemberId, Long applicantId);
}
