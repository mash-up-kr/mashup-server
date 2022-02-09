package kr.mashup.branding.domain.application.result;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
@ToString(of = {"applicationResultId", "status", "createdAt", "updatedAt"})
@EqualsAndHashCode(of = "applicationResultId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationResult {
    @Id
    @GeneratedValue
    private Long applicationResultId;

    @OneToOne
    @JoinColumn
    private Application application;

    @Enumerated(EnumType.STRING)
    private ApplicationResultStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // TODO: Add fields (createdBy, updatedBy)

    public static ApplicationResult of(Application application) {
        ApplicationResult applicationResult = new ApplicationResult();
        applicationResult.application = application;
        applicationResult.status = ApplicationResultStatus.TBD;
        return applicationResult;
    }

    public void update(ApplicationResultStatus status) {
        this.status = status.update(status);
    }
}
