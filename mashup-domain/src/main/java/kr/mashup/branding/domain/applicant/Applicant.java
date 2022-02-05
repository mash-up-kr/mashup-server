package kr.mashup.branding.domain.applicant;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "applicantId")
@EntityListeners(AuditingEntityListener.class)
public class Applicant {
    @Id
    @GeneratedValue
    private Long applicantId;

    private String email;

    private String googleUserId;

    private String name;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private ApplicantStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private static final Applicant TESTER;

    static {
        TESTER = new Applicant();
        TESTER.email = "mashup.12th.branding.server.dev@gmail.com";
        TESTER.googleUserId = "googleUserId";
        TESTER.name = "TESTER";
        TESTER.phoneNumber = "01031280428";
        TESTER.status = ApplicantStatus.ACTIVE;
    }

    public static Applicant tester() {
        return TESTER;
    }
}