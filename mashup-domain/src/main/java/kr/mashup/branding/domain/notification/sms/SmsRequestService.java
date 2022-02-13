package kr.mashup.branding.domain.notification.sms;

import java.util.List;

import kr.mashup.branding.domain.notification.Notification;

public interface SmsRequestService {
    List<SmsRequest> create(Notification notification, SmsSendRequestVo smsSendRequestVo);

    List<SmsRequest> getSmsRequests(Long notificationId);
}
