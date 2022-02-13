package kr.mashup.branding.domain.notification.sms;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.notification.Notification;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(of = {"smsRequestId", "status", "recipientPhoneNumber", "messageId", "resultId", "resultCode",
    "resultMessage", "createdAt", "updatedAt"})
@EqualsAndHashCode(of = "smsRequestId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SmsRequest {
    @Id
    @GeneratedValue
    private Long smsRequestId;

    @ManyToOne
    @JoinColumn(name = "notification_id", nullable = false, updatable = false)
    private Notification notification;

    @ManyToOne
    private Applicant recipientApplicant;

    @Enumerated(EnumType.STRING)
    private SmsNotificationStatus status = SmsNotificationStatus.CREATED;

    private String recipientPhoneNumber;

    /**
     * client 에서 생성한 메시지 식별자
     */
    private String messageId;

    /**
     * 외부 서비스에서 생성한 식별자
     */
    private String resultId;

    /**
     * 결과 코드
     */
    private String resultCode;

    /**
     * 결과 메시지
     */
    private String resultMessage;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static SmsRequest of(
        Notification notification,
        Applicant recipientApplicant
    ) {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.notification = notification;
        smsRequest.messageId = UUID.randomUUID().toString();
        smsRequest.recipientApplicant = recipientApplicant;
        smsRequest.recipientPhoneNumber = recipientApplicant.getPhoneNumber();
        return smsRequest;
    }

    public void setResult(SmsSendResultRecipientVo smsSendResultRecipientVo) {
        this.status = smsSendResultRecipientVo.getStatus();
        this.resultId = smsSendResultRecipientVo.getRequestId();
        this.resultCode = smsSendResultRecipientVo.getResultCode();
        this.resultMessage = smsSendResultRecipientVo.getResultMessage();
    }
}
