package kr.mashup.branding.domain.application.result;

public enum ApplicationResultStatus {
    TBD("미검토"),
    DOCUMENT_TBD("서류 미정"),
    DOCUMENT_PASSED("서류 합격"),
    DOCUMENT_FAILED("서류 불합격"),
    INTERVIEW_TBD("면접 미정"),
    INTERVIEW_PASSED("면접 합격"),
    INTERVIEW_FAILED("면접 불합격");

    ApplicationResultStatus(String description) {
    }

    ApplicationResultStatus update(ApplicationResultStatus status) {
        // TODO: validation 필요한지 논의해봐야함
        switch (this) {
            case TBD:
            case DOCUMENT_TBD:
            case DOCUMENT_PASSED:
            case DOCUMENT_FAILED:
            case INTERVIEW_TBD:
            case INTERVIEW_PASSED:
            case INTERVIEW_FAILED:
                return status;
        }
        throw new IllegalStateException();
    }
}
