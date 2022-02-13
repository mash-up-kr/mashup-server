package kr.mashup.branding.domain.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.mashup.branding.domain.notification.sms.SmsSendRequestVo;
import kr.mashup.branding.domain.notification.sms.SmsSendResultVo;

public interface NotificationService {
    NotificationDetailVo create(Long adminMemberId, SmsSendRequestVo smsSendRequestVo);

    NotificationDetailVo update(Long notificationId, SmsSendResultVo smsSendResultVo);

    Page<Notification> getNotifications(Pageable pageable);

    Notification getNotification(Long notificationId);
}
