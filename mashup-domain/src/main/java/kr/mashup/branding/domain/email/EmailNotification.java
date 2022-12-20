package kr.mashup.branding.domain.email;


import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
     * 이메일 템플릿
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EmailTemplate emailTemplate;

    /**
     * 발송 이메일
     */
    private String senderEmail;

    /**
     * 발송 메모
     */
    private String memo;

    @OneToMany(mappedBy = "emailNotification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailRequest> emailRequests;


    public static EmailNotification of(
        AdminMember sender,
        Generation generation,
        EmailTemplate emailTemplate,
        String memo,
        List<Application> applications
    ) {

        final EmailNotification emailNotification
            = new EmailNotification(sender, generation, emailTemplate, "recruit.mashup@gmail.com", memo);

        emailNotification.emailRequests
            = applications
            .stream()
            .map(it->EmailRequest.of(emailNotification,it))
            .collect(Collectors.toList());

        return emailNotification;
    }

    private EmailNotification(
        AdminMember sender,
        Generation generation,
        EmailTemplate emailTemplate,
        String senderEmail,
        String memo
    ) {

        this.sender = sender;
        this.generation = generation;
        this.emailTemplate = emailTemplate;
        this.senderEmail = senderEmail;
        this.memo = memo;
    }

}
