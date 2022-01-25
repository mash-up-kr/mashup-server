package kr.mashup.branding.domain.sms;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Entity
public class SmsRequestGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "smsRequestGroup")
    private List<SmsRequest> smsRequests = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private SmsRequestGroupStatus status = SmsRequestGroupStatus.IN_PROGRESS;

    private String description;

    private String content;

    private LocalDateTime createdAt;

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
