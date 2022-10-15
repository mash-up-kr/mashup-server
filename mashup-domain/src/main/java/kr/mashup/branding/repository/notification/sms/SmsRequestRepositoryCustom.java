package kr.mashup.branding.repository.notification.sms;

import kr.mashup.branding.domain.notification.sms.SmsRequest;

import java.util.List;

public interface SmsRequestRepositoryCustom {
    List<SmsRequest> findByRecipient(Long applicantId);
}
