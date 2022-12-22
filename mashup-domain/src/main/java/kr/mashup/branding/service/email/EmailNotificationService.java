package kr.mashup.branding.service.email;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.email.EmailNotification;
import kr.mashup.branding.domain.email.EmailRequest;
import kr.mashup.branding.domain.email.EmailRequestStatus;
import kr.mashup.branding.domain.email.EmailTemplate;
import kr.mashup.branding.domain.email.EmailTemplateName;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.repository.emailnotification.EmailNotificationRepository;
import kr.mashup.branding.repository.emailnotification.EmailRequestRepository;
import kr.mashup.branding.repository.emailnotification.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailNotificationService {

    private final EmailRequestRepository emailRequestRepository;
    private final EmailNotificationRepository emailNotificationRepository;
    private final EmailTemplateRepository emailTemplateRepository;

    public EmailNotification create(
        AdminMember sender,
        Generation generation,
        String memo,
        String templateName,
        List<Application> applications
    ){
        final EmailTemplateName emailTemplateName = EmailTemplateName.valueOf(templateName);

        final EmailTemplate emailTemplate =
            emailTemplateRepository.findByTemplateName(emailTemplateName).orElseThrow(IllegalArgumentException::new);

        final EmailNotification emailNotification
            = EmailNotification.of(sender, generation, emailTemplate, memo, applications);

        return emailNotificationRepository.save(emailNotification);
    }

    public EmailNotification getByIdOrThrow(Long emailNotificationId) {
        return emailNotificationRepository.findById(emailNotificationId).orElseThrow(IllegalArgumentException::new);
    }

    public void update(Long emailRequestId, EmailRequestStatus status){

        final EmailRequest emailRequest
            = emailRequestRepository.findById(emailRequestId).orElseThrow(IllegalArgumentException::new);

        emailRequest.updateStatus(status);
    }

    public Page<EmailNotification> readEmailNotifications(String searchWord, Pageable pageable) {
        return emailNotificationRepository.findByMemoContaining(searchWord, pageable);
    }
}
