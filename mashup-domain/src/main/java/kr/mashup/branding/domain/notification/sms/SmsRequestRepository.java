package kr.mashup.branding.domain.notification.sms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface SmsRequestRepository extends JpaRepository<SmsRequest, Long> {
    List<SmsRequest> findByNotification_notificationIdAndStatus(Long notificationId, SmsNotificationStatus status);

    List<SmsRequest> findByNotification_notificationId(Long notificationId);
}
