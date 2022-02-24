package kr.mashup.branding.ui.application;

import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;

/**
 * 지원자가 보는 지원 현황 > 지원 상태
 */
public enum ApplicationStatusResponse {
    WRITING("임시저장"),
    SUBMITTED("제출완료"),
    SCREENING_EXPIRED("기한만료"), // 임시저장까지 했는데 서류제출기간이 지난 경우
    SCREENING_FAILED("서류불합격"),
    SCREENING_PASSED("서류합격"),
    INTERVIEW_FAILED("면접불합격"),
    INTERVIEW_PASSED("최종합격"),
    ;

    ApplicationStatusResponse(String description) {
    }

    /**
     * 서류 합격 발표 전 지원상태
     */
    static ApplicationStatusResponse submitted(ApplicationStatus status) {
        if (status.isSubmitted()) {
            return SUBMITTED;
        }
        return WRITING;
    }

    /**
     * 서류 합격 발표 후 ~ 면접 합격 발표 전 지원상태
     */
    static ApplicationStatusResponse screeningResult(
        ApplicationStatus applicationStatus,
        ApplicationScreeningStatus screeningStatus
    ) {
        if (applicationStatus == ApplicationStatus.WRITING) {
            return SCREENING_EXPIRED;
        }
        if (screeningStatus == ApplicationScreeningStatus.PASSED) {
            return SCREENING_PASSED;
        }
        return SCREENING_FAILED;
    }

    /**
     * 면접 합격 발표 후 지원상태
     */
    static ApplicationStatusResponse interviewResult(
        ApplicationScreeningStatus screeningStatus,
        ApplicationInterviewStatus interviewStatus
    ) {
        if (screeningStatus == ApplicationScreeningStatus.FAILED) {
            return SCREENING_FAILED;
        }
        if (interviewStatus == ApplicationInterviewStatus.PASSED) {
            return INTERVIEW_PASSED;
        }
        return INTERVIEW_FAILED;
    }
}
