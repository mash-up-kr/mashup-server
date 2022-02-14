package kr.mashup.branding.domain.notification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.notification.sms.SmsSendRequestVo;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(of = {"notificationId", "senderValue", "sentAt", "name", "content", "status", "type", "messageId", "resultId",
    "resultCode", "resultMessage", "createdAt", "updatedAt"})
@EqualsAndHashCode(of = "notificationId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    @Id
    @GeneratedValue
    private Long notificationId;

    /**
     * 발송자
     */
    @ManyToOne
    @JoinColumn(name = "admin_member_id")
    private AdminMember sender;

    /**
     * 발송자 번호
     */
    private String senderValue;

    /**
     * 발송 시각
     */
    private LocalDateTime sentAt;

    /**
     * 발송메모
     */
    @Column(unique = true)
    private String name;

    /**
     * 발송 내용
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 발송 상태
     */
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    /**
     * 발송 종류 (SMS, EMAIL, ...)
     */
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    /**
     * client 가 생성한 메시지 식별자
     */
    private String messageId;

    /**
     * 외부 서비스에서 생성한 메시지 식별자
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

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    private final List<SmsRequest> smsRequests = new ArrayList<>();

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Notification sms(
        AdminMember adminMember,
        SmsSendRequestVo smsSendRequestVo
    ) {
        Notification notification = new Notification();
        notification.sender = adminMember;
        notification.senderValue = adminMember.getPhoneNumber();
        notification.sentAt = LocalDateTime.now();
        notification.messageId = UUID.randomUUID().toString();
        notification.name = smsSendRequestVo.getName();
        if (!StringUtils.hasText(notification.name)) {
            throw new IllegalArgumentException("'name' must not be null, empty or blank");
        }
        notification.content = smsSendRequestVo.getContent();
        notification.status = NotificationStatus.CREATED;
        notification.type = NotificationType.SMS;
        return notification;
    }

    public String getSenderPhoneNumber() {
        return senderValue;
    }

    public void markToUnknown() {
        status = NotificationStatus.UNKNOWN;
    }

    public void markResult(
        NotificationStatus status,
        String resultId,
        String resultCode,
        String resultMessage
    ) {
        this.status = status;
        this.resultId = resultId;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
