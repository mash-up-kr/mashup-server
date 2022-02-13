package kr.mashup.branding.domain.sms;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "smsRequestGroupId")
@NoArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SmsRequestGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smsRequestGroupId;

    @OneToMany(mappedBy = "smsRequestGroup")
    private List<SmsRequest> smsRequests = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private SmsRequestGroupStatus status = SmsRequestGroupStatus.IN_PROGRESS;

    private String description;

    private String content;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    private SmsRequestGroup(String content, String description) {
        this.content = content;
        this.description = description;
    }

    void setStatus(SmsRequestGroupStatus status) {
        this.status = status;
    }
}
