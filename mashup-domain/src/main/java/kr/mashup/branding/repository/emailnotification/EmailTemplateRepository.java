package kr.mashup.branding.repository.emailnotification;

import kr.mashup.branding.domain.email.EmailTemplate;
import kr.mashup.branding.domain.email.EmailTemplateName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    Optional<EmailTemplate> findByTemplateName(EmailTemplateName emailTemplateName);
}
