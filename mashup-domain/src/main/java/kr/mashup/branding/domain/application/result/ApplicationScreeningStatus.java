package kr.mashup.branding.domain.application.result;

/**
 * 서류 평가 상태
 */
public enum ApplicationScreeningStatus {
    NOT_RATED("미검토"),
    NOT_APPLICABLE("대상 아님"),
    FAILED("불합격"),
    TO_BE_DETERMINED("보류"),
    PASSED("합격"),
    ;

    ApplicationScreeningStatus(String description) {
    }

    ApplicationScreeningStatus update(ApplicationScreeningStatus screeningStatus) {
        switch (this) {
            case NOT_RATED:
            case NOT_APPLICABLE:
            case FAILED:
            case TO_BE_DETERMINED:
            case PASSED:
                return screeningStatus;
        }
        throw new IllegalStateException();
    }
}
