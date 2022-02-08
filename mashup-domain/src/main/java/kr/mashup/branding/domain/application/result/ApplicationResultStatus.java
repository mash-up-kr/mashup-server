package kr.mashup.branding.domain.application.result;

public enum ApplicationResultStatus {
    NOT_RATED("미검토"),
    SCREENING_FAILED("서류 불합격"),
    SCREENING_TBD("서류 미정"),
    SCREENING_PASSED("서류 합격"),
    INTERVIEW_FAILED("면접 불합격"),
    INTERVIEW_TBD("면접 미정"),
    INTERVIEW_PASSED("면접 합격"),
    ;

    ApplicationResultStatus(String description) {
    }

    ApplicationResultStatus update(ApplicationResultStatus status) {
        switch (this) {
            case NOT_RATED:
            case SCREENING_FAILED:
            case SCREENING_TBD:
            case SCREENING_PASSED:
            case INTERVIEW_FAILED:
            case INTERVIEW_TBD:
            case INTERVIEW_PASSED:
                return status;
        }
        throw new IllegalStateException();
    }

    boolean isInterviewTimeAvailable() {
        switch (this) {
            case NOT_RATED:
            case SCREENING_FAILED:
            case SCREENING_TBD:
                return false;
            case SCREENING_PASSED:
            case INTERVIEW_FAILED:
            case INTERVIEW_TBD:
            case INTERVIEW_PASSED:
                return true;
        }
        throw new IllegalStateException();
    }
}
