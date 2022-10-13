package kr.mashup.branding.repository.notification.sms;

import kr.mashup.branding.domain.notification.sms.SmsNotificationStatus;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmsRequestRepository extends JpaRepository<SmsRequest, Long> {
    List<SmsRequest> findByNotification_notificationIdAndStatus(Long notificationId, SmsNotificationStatus status);

    List<SmsRequest> findByNotification_notificationId(Long notificationId);

    List<SmsRequest> findByRecipientApplicant_applicantId(Long applicantId);
}
/**
 * Sms Request 연관관계
 * many to one : notification, applicant
 */