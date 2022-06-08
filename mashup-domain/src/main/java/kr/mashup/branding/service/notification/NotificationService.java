package kr.mashup.branding.service.notification;

import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.NotificationDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.mashup.branding.domain.notification.sms.SmsSendRequestVo;
import kr.mashup.branding.domain.notification.sms.SmsSendResultVo;

public interface NotificationService {
    NotificationDetailVo create(Long adminMemberId, SmsSendRequestVo smsSendRequestVo);

    NotificationDetailVo update(Long notificationId, SmsSendResultVo smsSendResultVo);

    Page<Notification> getNotifications(Long adminMemberId, String searchWord, Pageable pageable);

    Notification getNotification(Long notificationId);
}
