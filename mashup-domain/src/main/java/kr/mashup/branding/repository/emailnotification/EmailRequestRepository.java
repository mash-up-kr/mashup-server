package kr.mashup.branding.repository.emailnotification;

import kr.mashup.branding.domain.email.EmailRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRequestRepository extends JpaRepository<EmailRequest, Long> {
}
