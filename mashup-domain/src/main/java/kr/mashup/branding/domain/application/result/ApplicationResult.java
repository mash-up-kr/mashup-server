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
@ToString
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

    /**
     * 평가 결과
     */
    @Enumerated(EnumType.STRING)
    private ApplicationResultStatus status;

    /**
     * 면접 시작 시각
     */
    private LocalDateTime interviewStartedAt;

    /**
     * 면접 종료 시각
     */
    private LocalDateTime interviewEndedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // TODO: Add fields (createdBy, updatedBy)

    public static ApplicationResult of(Application application) {
        ApplicationResult applicationResult = new ApplicationResult();
        applicationResult.application = application;
        applicationResult.status = ApplicationResultStatus.NOT_RATED;
        return applicationResult;
    }

    public void update(ApplicationResultStatus status) {
        this.status = status.update(status);
    }

    /**
     * 합격 여부와 면접 시간을 변경한다.
     * 면접 시작 시각은 항상 면접 종료 시각보다 이른 시각이어야 한다.
     * 면접 종료 시각만 입력할수는 없다. (시작, 종료시각 모두 null 이거나, 모두 not-null 은 가능. 시작시간만 not-null 인 경우도 가능)
     */
    public void update(UpdateApplicationResultVo updateApplicationResultVo) {
        this.status = status.update(updateApplicationResultVo.getStatus());
        if (!status.isInterviewTimeAvailable()) {
            throw new IllegalArgumentException("'status' is not available for interviewTime. status: " + status);
        }
        if (interviewStartedAt == null) {
            return;
        }
        this.interviewStartedAt = updateApplicationResultVo.getInterviewStartedAt();
        if (!interviewEndedAt.isAfter(interviewStartedAt)) {
            throw new InterviewTimeInvalidException(
                "'interviewEndedAt' must be after or equal to 'interviewStartedAt'. interviewStartedAt: "
                    + interviewStartedAt + ", interviewEndedAt: " + interviewEndedAt);
        }
        this.interviewEndedAt = updateApplicationResultVo.getInterviewEndedAt();
    }

    public void setInterviewStartedAt(LocalDateTime interviewStartedAt) {
        this.interviewStartedAt = interviewStartedAt;
    }
}
