package kr.mashup.branding.domain.application.confirmation;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.application.Application;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(of = {"confirmationId", "status", "createdAt", "updatedAt"})
@EqualsAndHashCode(of = "confirmationId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Confirmation {
    @Id
    @GeneratedValue
    private Long confirmationId;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Enumerated(EnumType.STRING)
    private ApplicantConfirmationStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Confirmation of(Application application) {
        Confirmation confirmation = new Confirmation();
        confirmation.application = application;
        confirmation.status = ApplicantConfirmationStatus.TO_BE_DETERMINED;
        return confirmation;
    }

    public void updateFromApplicant(ApplicantConfirmationStatus status) {
        this.status = this.status.updateFromApplicant(status);
    }

    public void updateFromAdmin(ApplicationScreeningStatus screeningStatus, ApplicationInterviewStatus interviewStatus) {
        this.status = this.status.updateFromAdmin(screeningStatus, interviewStatus);
    }

    public void updateApplicantConfirmationStatus(ApplicantConfirmationStatus status) {
        this.status = status;
    }
}
