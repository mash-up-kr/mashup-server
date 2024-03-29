package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;

/**
 * 지원자가 보는 지원 현황 > 지원 상태
 */
public enum ApplicationStatusResponse {
    WRITING("임시저장"),
    SUBMITTED("제출 완료"),
    SCREENING_EXPIRED("기한 만료"), // 임시저장까지 했는데 서류제출기간이 지난 경우
    SCREENING_FAILED("서류 불합격"),
    SCREENING_PASSED("서류 합격"),
    INTERVIEW_FAILED("최종 불합격"),
    INTERVIEW_PASSED("최종 합격"),
    ;

    ApplicationStatusResponse(String description) {
    }

    /**
     * 서류 마감 전 지원상태
     */
    public static ApplicationStatusResponse submitted(ApplicationStatus status) {
        if (status.isSubmitted()) {
            return SUBMITTED;
        }
        return WRITING;
    }

    /**
     * 서류 마감 후 ~ 서류 합격 발표 전 지원상태
     */
    public static ApplicationStatusResponse beforeResult(ApplicationStatus status) {
        if (!status.isSubmitted()) {
            return SCREENING_EXPIRED;
        }
        return SUBMITTED;
    }

    /**
     * 서류 합격 발표 후 ~ 면접 합격 발표 전 지원상태
     */
    public static ApplicationStatusResponse screeningResult(
        ApplicationStatus applicationStatus,
        ApplicationScreeningStatus screeningStatus
    ) {
        switch (applicationStatus) {
            case CREATED:
            case WRITING:
                return SCREENING_EXPIRED;
        }
        switch (screeningStatus) {
            case NOT_APPLICABLE:
                return SCREENING_EXPIRED;
            case NOT_RATED:
            case FAILED:
            case TO_BE_DETERMINED:
                return SCREENING_FAILED;
        }
        return SCREENING_PASSED;
    }

    /**
     * 면접 합격 발표 후 지원상태
     */
    public static ApplicationStatusResponse interviewResult(
        ApplicationScreeningStatus screeningStatus,
        ApplicationInterviewStatus interviewStatus,
        ApplicantConfirmationStatus confirmationStatus
    ) {
        switch (screeningStatus) {
            case NOT_APPLICABLE:
                return SCREENING_EXPIRED;
            case NOT_RATED:
            case FAILED:
            case TO_BE_DETERMINED:
                return SCREENING_FAILED;
        }
        switch (interviewStatus) {
            case NOT_APPLICABLE:
                return SCREENING_FAILED;
            case NOT_RATED:
                if (screeningStatus == ApplicationScreeningStatus.PASSED
                    && confirmationStatus != ApplicantConfirmationStatus.INTERVIEW_CONFIRM_ACCEPTED) {
                    return SCREENING_PASSED;
                }
            case FAILED:
            case TO_BE_DETERMINED:
                return INTERVIEW_FAILED;
        }
        return INTERVIEW_PASSED;
    }
}
