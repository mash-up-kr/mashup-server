package kr.mashup.branding.facade.emailnotification;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.email.EmailMetadata;
import kr.mashup.branding.domain.email.EmailNotification;
import kr.mashup.branding.domain.email.EmailRequest;
import kr.mashup.branding.domain.email.EmailTemplate;
import kr.mashup.branding.domain.email.EmailTemplateName;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.service.email.EmailNotificationEvent;
import kr.mashup.branding.service.email.EmailNotificationEventPublisher;
import kr.mashup.branding.service.email.EmailNotificationService;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.ui.emailnotification.EmailSendRequest;
import kr.mashup.branding.ui.emailnotification.vo.EmailNotificationDetailResponseVo;
import kr.mashup.branding.ui.emailnotification.vo.EmailNotificationResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailNotificationFacadeService {

    private final AdminMemberService adminMemberService;
    private final GenerationService generationService;
    private final EmailNotificationService emailNotificationService;
    private final EmailNotificationEventPublisher emailNotificationEventPublisher;
    private final ApplicationService applicationService;

    @Transactional
    public void sendEmailNotification(final Long adminMemberId, final Integer generationNumber, final EmailSendRequest request) {

        final AdminMember adminMember
            = adminMemberService.getByAdminMemberId(adminMemberId);

        final Generation generation
            = generationService.getByNumberOrThrow(generationNumber);

        final List<Application> applications
            = applicationService.getApplications(request.getApplicationIds());

        final EmailNotification emailNotification
            = emailNotificationService.create(adminMember, generation, request.getMemo(), request.getTemplateName(), applications);

        final EmailNotificationEvent event
            = EmailNotificationEvent.of(emailNotification.getId());

        emailNotificationEventPublisher.publishEmailNotificationEvent(event);
    }

    public List<EmailMetadata> getEmailMetaData(final Long emailNotificationId) {

        final EmailNotification emailNotification
            = emailNotificationService.getByIdOrThrow(emailNotificationId);

        final EmailTemplate emailTemplate
            = emailNotification.getEmailTemplate();

        final EmailTemplateName templateName = emailTemplate.getTemplateName();

        final String senderEmail = emailNotification.getSenderEmail();
        final List<EmailRequest> emailRequests = emailNotification.getEmailRequests();

        final List<EmailMetadata> metadataList = new ArrayList<>();

        for (final EmailRequest request : emailRequests) {

            final Application application = request.getApplication();
            // TODO : 템플릿 분기
            final Map<String, String> bindingData = new HashMap<>();
            if (templateName.equals(EmailTemplateName.SUBMIT)) {
                bindingData.put("name", application.getApplicant().getName());
                bindingData.put("position", application.getApplicationForm().getTeam().getName());
            }else{
                bindingData.put("name", application.getApplicant().getName());
                bindingData.put("position", application.getApplicationForm().getTeam().getName());
            }

            final EmailMetadata emailMetadata
                = EmailMetadata.of(request.getId(), templateName.name(), bindingData, senderEmail,
                application.getApplicant().getEmail());

            metadataList.add(emailMetadata);
        }

        return metadataList;
    }

    @Transactional(readOnly = true)
    public Page<EmailNotificationResponseVo> readEmailNotifications(String searchWord, Pageable pageable) {
        return emailNotificationService.readEmailNotifications(searchWord, pageable)
                .map(EmailNotificationResponseVo::of);
    }

    @Transactional(readOnly = true)
    public EmailNotificationDetailResponseVo readEmailNotification(Long emailNotificationId) {
        return EmailNotificationDetailResponseVo.of(emailNotificationService.readEmailNotification(emailNotificationId));
    }
}
