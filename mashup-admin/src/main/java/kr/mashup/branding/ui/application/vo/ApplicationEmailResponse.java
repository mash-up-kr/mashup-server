package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.adminmember.entity.Position;
import kr.mashup.branding.domain.email.EmailNotification;
import kr.mashup.branding.domain.email.EmailRequest;
import kr.mashup.branding.domain.email.EmailRequestStatus;
import kr.mashup.branding.domain.email.EmailTemplateName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplicationEmailResponse {

    private Long emailNotificationId;

    private Long emailRequestId;

    private EmailRequestStatus emailRequestStatus;

    private String memo;

    private Position senderPosition;

    private EmailTemplateName templateName;

    private LocalDateTime sendAt;

    public static ApplicationEmailResponse of(EmailRequest emailRequest){

        final EmailNotification emailNotification
            = emailRequest.getEmailNotification();

        return new ApplicationEmailResponse(
            emailNotification.getId(),
            emailRequest.getId(),
            emailRequest.getStatus(),
            emailNotification.getMemo(),
            emailNotification.getSender().getPosition(),
            emailNotification.getEmailTemplateName(),
            emailNotification.getCreatedAt());
    }
}
