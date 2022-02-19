package kr.mashup.branding.facade.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.NotificationDetailVo;
import kr.mashup.branding.domain.notification.sms.SmsSendRequestVo;

public interface NotificationFacadeService {
    NotificationDetailVo sendSms(Long adminMemberId, SmsSendRequestVo smsSendRequestVo);

    Page<Notification> getNotifications(Long adminMemberId, String searchWord, Pageable pageable);

    NotificationDetailVo getNotificationDetail(Long adminMemberId, Long getNotification);
}
