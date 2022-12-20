package kr.mashup.branding.repository.emailnotification;

import kr.mashup.branding.domain.email.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {

}
