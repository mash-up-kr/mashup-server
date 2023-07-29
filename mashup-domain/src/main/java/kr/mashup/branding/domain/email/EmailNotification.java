package kr.mashup.branding.domain.email;


import kr.mashup.branding.config.EmailConfig;
import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.generation.Generation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EmailNotification {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 발송자
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_member_id")
    private AdminMember sender;

    /**
     * 기수
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Generation generation;

    /**
     * 발송 이메일
     */
    private String senderEmail;

    /**
     * 발송 메모
     */
    private String memo;

    /**
     * 발송 템플릿 이름
     */
    @Enumerated(EnumType.STRING)
    private EmailTemplateName emailTemplateName;

    @OneToMany(mappedBy = "emailNotification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailRequest> emailRequests;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static EmailNotification of(
        AdminMember sender,
        Generation generation,
        EmailTemplateName emailTemplateName,
        String memo,
        List<Application> applications
    ) {

        final EmailNotification emailNotification
            = new EmailNotification(sender, generation, emailTemplateName, EmailConfig.RECRUIT_ADDRESS, memo);

        emailNotification.emailRequests
            = applications
            .stream()
            .map(it -> EmailRequest.of(emailNotification, it))
            .collect(Collectors.toList());

        return emailNotification;
    }

    private EmailNotification(
        AdminMember sender,
        Generation generation,
        EmailTemplateName emailTemplateName,
        String senderEmail,
        String memo
    ) {

        this.sender = sender;
        this.generation = generation;
        this.emailTemplateName = emailTemplateName;
        this.senderEmail = senderEmail;
        this.memo = memo;
    }

}
