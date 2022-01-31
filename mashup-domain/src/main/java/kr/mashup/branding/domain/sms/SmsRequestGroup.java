package kr.mashup.branding.domain.sms;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @CreatedDate
    private LocalDateTime createdAt;

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
