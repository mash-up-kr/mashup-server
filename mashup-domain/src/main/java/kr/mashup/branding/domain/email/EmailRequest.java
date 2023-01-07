package kr.mashup.branding.domain.email;

import kr.mashup.branding.domain.application.Application;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EmailRequest {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EmailNotification emailNotification;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Application application;

    private String messageId = "";

    @Enumerated(EnumType.STRING)
    private EmailRequestStatus status = EmailRequestStatus.CREATED;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updateStatus(EmailRequestStatus status) {
        this.status = status;
    }

    public void setMessageId(String messageId){
        this.messageId = messageId;
    }

    public static EmailRequest of(EmailNotification emailNotification, Application application) {
        return new EmailRequest(emailNotification, application);
    }

    private EmailRequest(EmailNotification emailNotification, Application application) {
        this.emailNotification = emailNotification;
        this.application = application;
    }
}
