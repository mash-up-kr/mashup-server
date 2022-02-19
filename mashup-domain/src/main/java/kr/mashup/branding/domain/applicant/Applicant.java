package kr.mashup.branding.domain.applicant;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

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
@ToString(of = {"applicantId", "name", "status", "createdAt", "updatedAt"})
@EqualsAndHashCode(of = "applicantId")
@EntityListeners(AuditingEntityListener.class)
public class Applicant {
    private static final Pattern PATTERN_NUMBER = Pattern.compile("\\d+");

    @Id
    @GeneratedValue
    private Long applicantId;

    private String email;

    private String googleUserId;

    private String name;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private ApplicantStatus status = ApplicantStatus.ACTIVE;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Applicant() {
    }

    public static Applicant of(String email, String googleUserId) {
        Applicant applicant = new Applicant();
        applicant.email = email;
        applicant.googleUserId = googleUserId;
        return applicant;
    }

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

    public void update(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber.replaceAll("-", "").trim();
        if (!PATTERN_NUMBER.matcher(this.phoneNumber).matches()) {
            throw new IllegalArgumentException("'phoneNumber' must be include number and hyphen only");
        }
    }
}
