package kr.mashup.branding.domain.application.result;

public enum ApplicationInterviewStatus {
    NOT_RATED("미검토"),
    NOT_APPLICABLE("대상 아님"),
    FAILED("불합격"),
    TBD("미정"),
    PASSED("합격"),
    ;

    ApplicationInterviewStatus(String description) {
    }

    public ApplicationInterviewStatus update(ApplicationInterviewStatus interviewStatus) {
        switch (this) {
            case NOT_RATED:
            case NOT_APPLICABLE:
            case FAILED:
            case TBD:
            case PASSED:
                return interviewStatus;
        }
        throw new IllegalStateException();
    }
}
