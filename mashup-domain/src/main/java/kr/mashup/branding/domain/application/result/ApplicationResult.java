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

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
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
@ToString(of = {"applicationResultId", "screeningStatus", "interviewStatus", "createdBy", "createdAt", "updatedBy",
    "updatedAt"})
@EqualsAndHashCode(of = "applicationResultId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationResult {
    @Id
    @GeneratedValue
    private Long applicationResultId;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    /**
     * 서류 평가 결과
     */
    @Enumerated(EnumType.STRING)
    private ApplicationScreeningStatus screeningStatus;

    /**
     * 면접 평가 결과
     */
    @Enumerated(EnumType.STRING)
    private ApplicationInterviewStatus interviewStatus;

    /**
     * 면접 시작 시각
     */
    private LocalDateTime interviewStartedAt;

    /**
     * 면접 종료 시각
     */
    private LocalDateTime interviewEndedAt;

    /**
     * 면접 안내 링크 (오픈채팅방 링크 or 화상미팅 링크)
     */
    private String interviewGuideLink;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static ApplicationResult of(Application application) {
        ApplicationResult applicationResult = new ApplicationResult();
        applicationResult.application = application;
        applicationResult.screeningStatus = ApplicationScreeningStatus.NOT_RATED;
        applicationResult.interviewStatus = ApplicationInterviewStatus.NOT_RATED;
        return applicationResult;
    }

    /**
     * 합격 여부와 면접 시간을 변경한다.
     * 면접 시작 시각은 항상 면접 종료 시각보다 이른 시각이어야 한다.
     * 면접 종료 시각만 입력할수는 없다. (시작, 종료시각 모두 null 이거나, 모두 not-null 은 가능. 시작시간만 not-null 인 경우도 가능)
     */
    public void updateResult(UpdateApplicationResultVo updateApplicationResultVo) {
        // 합격 상태 변경
        if (updateApplicationResultVo.getScreeningStatus() != null) {
            screeningStatus = screeningStatus.update(updateApplicationResultVo.getScreeningStatus());
        }
        if (updateApplicationResultVo.getInterviewStatus() != null) {
            interviewStatus = interviewStatus.update(updateApplicationResultVo.getInterviewStatus());
        }
        // 면접 일정 변경
        if (updateApplicationResultVo.getInterviewStartedAt() == null) {
            return;
        }
        if (screeningStatus != ApplicationScreeningStatus.PASSED) {
            throw new IllegalArgumentException(
                "'screeningStatus' is not available for interviewTime. screeningStatus: " + screeningStatus);
        }
        this.interviewStartedAt = updateApplicationResultVo.getInterviewStartedAt();
        if (!updateApplicationResultVo.getInterviewEndedAt().isAfter(interviewStartedAt)) {
            throw new InterviewTimeInvalidException(
                "'interviewEndedAt' must be after or equal to 'interviewStartedAt'. interviewStartedAt: "
                    + interviewStartedAt + ", interviewEndedAt: " + interviewEndedAt);
        }
        this.interviewEndedAt = updateApplicationResultVo.getInterviewEndedAt();
        // 면접 안내 링크 변경
        this.interviewGuideLink = updateApplicationResultVo.getInterviewGuideLink();
    }
}
