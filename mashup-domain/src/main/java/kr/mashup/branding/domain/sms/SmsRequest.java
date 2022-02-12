package kr.mashup.branding.domain.sms;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.adminmember.AdminMember;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "smsRequestId")
@NoArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SmsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smsRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sms_request_group_id", nullable = false, updatable = false)
    private SmsRequestGroup smsRequestGroup;

    @Enumerated(EnumType.STRING)
    private SmsRequestStatus status = SmsRequestStatus.IN_PROGRESS;

    private String smsSendKey;

    private Long applicantId;

    private String applicantName;

    private String phoneNumber;

    @CreatedBy
    @ManyToOne
    private AdminMember createdBy;

    @LastModifiedBy
    @ManyToOne
    private AdminMember updatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static SmsRequest of(SmsRequestGroup smsRequestGroup, Long applicantId, String applicantName,
        String phoneNumber) {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.smsRequestGroup = smsRequestGroup;
        smsRequest.applicantId = applicantId;
        smsRequest.applicantName = applicantName;
        smsRequest.phoneNumber = phoneNumber;
        return smsRequest;
    }

    public void markAsSuccess() {
        setStatus(SmsRequestStatus.SUCCESS);
    }

    public void markAsFail() {
        setStatus(SmsRequestStatus.FAIL);
    }

    public void updateSmsSendKey(String smsSendKey) {
        this.smsSendKey = smsSendKey;
    }

    private void setStatus(SmsRequestStatus status) {
        this.status = status;
    }
}
