package kr.mashup.branding.domain.application.progress;

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
@EqualsAndHashCode(of = "applicationProgressId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationProgress {
    @Id
    @GeneratedValue
    private Long applicationProgressId;

    @Enumerated(EnumType.STRING)
    private ApplicationProgressStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static ApplicationProgress of() {
        ApplicationProgress applicationProgress = new ApplicationProgress();
        applicationProgress.status = ApplicationProgressStatus.TBD;
        return applicationProgress;
    }

    public void updateFromApplicant(ApplicationProgressStatus status) {
        this.status = this.status.updateFromApplicant(status);
    }
}
