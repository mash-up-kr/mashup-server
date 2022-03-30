package kr.mashup.branding.domain.application.result;

/**
 * 면접 평가 상태
 */
public enum ApplicationInterviewStatus {
    NOT_RATED("미검토"),
    NOT_APPLICABLE("대상 아님"),
    FAILED("불합격"),
    TO_BE_DETERMINED("보류"),
    PASSED("합격"),
    ;

    ApplicationInterviewStatus(String description) {
    }

    public ApplicationInterviewStatus update(ApplicationInterviewStatus interviewStatus) {
        switch (this) {
            case NOT_RATED:
            case NOT_APPLICABLE:
            case FAILED:
            case TO_BE_DETERMINED:
            case PASSED:
                return interviewStatus;
        }
        throw new IllegalStateException();
    }
}
