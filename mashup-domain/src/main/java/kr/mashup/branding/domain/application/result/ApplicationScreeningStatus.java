package kr.mashup.branding.domain.application.result;

/**
 * 서류 평가 상태
 */
public enum ApplicationScreeningStatus {
    NOT_RATED("미검토"),
    FAILED("불합격"),
    TO_BE_DETERMINED("미정"),
    PASSED("합격"),
    ;

    ApplicationScreeningStatus(String description) {
    }

    ApplicationScreeningStatus update(ApplicationScreeningStatus status) {
        switch (this) {
            case NOT_RATED:
            case FAILED:
            case TO_BE_DETERMINED:
            case PASSED:
                return status;
        }
        throw new IllegalStateException();
    }
}
