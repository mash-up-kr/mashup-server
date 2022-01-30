package kr.mashup.branding.domain.sms;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Entity
public class SmsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sms_request_id", nullable = false, updatable = false)
    private SmsRequestGroup smsRequestGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SmsRequestStatus status = SmsRequestStatus.IN_PROGRESS;

    private String toastKey;

    private Long userId;

    private String username;

    private String phoneNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    private SmsRequest(SmsRequestGroup smsRequestGroup, String toastKey, Long userId, String username, String phoneNumber) {
        this.toastKey = toastKey;
        this.smsRequestGroup = smsRequestGroup;
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }
    public void markAsSuccess() {
        setStatus(SmsRequestStatus.SUCCESS);
    }
    public void markAsFail() {
        setStatus(SmsRequestStatus.FAIL);
    }
    private void setStatus(SmsRequestStatus status) {
        this.status = status;
    }
}
