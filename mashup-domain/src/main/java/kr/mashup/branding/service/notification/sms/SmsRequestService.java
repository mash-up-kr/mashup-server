package kr.mashup.branding.service.notification.sms;

import java.util.List;

import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendRequestVo;

public interface SmsRequestService {
    List<SmsRequest> create(Notification notification, SmsSendRequestVo smsSendRequestVo);

    List<SmsRequest> getSmsRequestsByApplicantId(Long applicantId);
}
