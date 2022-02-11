package kr.mashup.branding.domain.application.confirmation;

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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "confirmationId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Confirmation {
    @Id
    @GeneratedValue
    private Long confirmationId;

    @Enumerated(EnumType.STRING)
    private ApplicantConfirmationStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Confirmation toBeDetermined() {
        Confirmation confirmation = new Confirmation();
        confirmation.status = ApplicantConfirmationStatus.TBD;
        return confirmation;
    }

    public void updateFromApplicant(ApplicantConfirmationStatus status) {
        this.status = this.status.updateFromApplicant(status);
    }
}
