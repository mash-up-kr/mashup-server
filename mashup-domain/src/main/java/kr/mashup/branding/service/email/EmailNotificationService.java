package kr.mashup.branding.service.email;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.email.EmailNotification;
import kr.mashup.branding.domain.email.EmailRequest;
import kr.mashup.branding.domain.email.EmailRequestStatus;
import kr.mashup.branding.domain.email.EmailTemplateName;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.repository.emailnotification.EmailNotificationRepository;
import kr.mashup.branding.repository.emailnotification.EmailRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailNotificationService {

    private final EmailRequestRepository emailRequestRepository;
    private final EmailNotificationRepository emailNotificationRepository;

    public EmailNotification create(
        final AdminMember sender,
        final Generation generation,
        final String memo,
        final EmailTemplateName templateName,
        final List<Application> applications
    ) {

        final EmailNotification emailNotification
            = EmailNotification.of(sender, generation, templateName, memo, applications);

        return emailNotificationRepository.save(emailNotification);
    }

    public EmailNotification getByIdOrThrow(
        final Long emailNotificationId
    ) {
        return emailNotificationRepository
            .findById(emailNotificationId)
            .orElseThrow(IllegalArgumentException::new);
    }

    public void updateToSuccess(final Long emailRequestId, final String messageId) {

        final EmailRequest emailRequest
            = emailRequestRepository.findById(emailRequestId).orElseThrow(IllegalArgumentException::new);

        emailRequest.updateStatus(EmailRequestStatus.SUCCESS);
        emailRequest.setMessageId(messageId);
    }

    public void updateToFail(final Long emailRequestId) {

        final EmailRequest emailRequest
            = emailRequestRepository.findById(emailRequestId).orElseThrow(IllegalArgumentException::new);

        emailRequest.updateStatus(EmailRequestStatus.FAIL);
    }

    public Page<EmailNotification> readEmailNotifications(
        final Optional<String> searchWord,
        final Pageable pageable
    ) {
        return emailNotificationRepository.findBySearchWord(searchWord, pageable);
    }

    public List<EmailRequest> getEmailRequestsByApplicationId(Long applicationId) {
        return emailRequestRepository.findByApplicationId(applicationId);
    }
}
