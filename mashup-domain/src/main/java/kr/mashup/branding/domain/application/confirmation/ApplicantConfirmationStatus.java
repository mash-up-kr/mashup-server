package kr.mashup.branding.domain.application.confirmation;

import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicantConfirmationStatus {
    TO_BE_DETERMINED("미검토"),
    INTERVIEW_CONFIRM_WAITING("면접 확인 대기"),
    INTERVIEW_CONFIRM_ACCEPTED("면접 수락"),
    INTERVIEW_CONFIRM_REJECTED("면접 거절"),
    FINAL_CONFIRM_WAITING("최종 확인 대기"),
    FINAL_CONFIRM_ACCEPTED("최종 수락"),
    FINAL_CONFIRM_REJECTED("최종 거절"),

    NOT_APPLICABLE("해당 없음");

    private final String description;

    public ApplicantConfirmationStatus updateFromApplicant(ApplicantConfirmationStatus status) {
        switch (this) {
            case INTERVIEW_CONFIRM_WAITING:
                if (status == INTERVIEW_CONFIRM_ACCEPTED || status == INTERVIEW_CONFIRM_REJECTED) {
                    return status;
                }
                break;
            case FINAL_CONFIRM_WAITING:
                if (status == FINAL_CONFIRM_ACCEPTED || status == FINAL_CONFIRM_REJECTED) {
                    return status;
                }
                break;
            case INTERVIEW_CONFIRM_ACCEPTED:
            case INTERVIEW_CONFIRM_REJECTED:
            case FINAL_CONFIRM_ACCEPTED:
            case FINAL_CONFIRM_REJECTED:
                if (status == this) {
                    return status;
                }
                break;
            case TO_BE_DETERMINED:
            case NOT_APPLICABLE:
                throw new ConfirmationUpdateInvalidException();
        }
        throw new IllegalStateException();
    }

    public ApplicantConfirmationStatus updateFromAdmin(ApplicationScreeningStatus screeningStatus, ApplicationInterviewStatus interviewStatus) {
        switch (this) {
            case TO_BE_DETERMINED:
            case INTERVIEW_CONFIRM_WAITING:
            case NOT_APPLICABLE:
                return updateByScreeningStatus(screeningStatus);
            case INTERVIEW_CONFIRM_ACCEPTED:
            case FINAL_CONFIRM_WAITING:
                return updateByInterviewStatus(interviewStatus);
            default:
                return this;
        }
    }

    private ApplicantConfirmationStatus updateByScreeningStatus(ApplicationScreeningStatus screeningStatus) {
        switch (screeningStatus) {
            case NOT_RATED:
                return TO_BE_DETERMINED;
            case FAILED:
            case TO_BE_DETERMINED:
                return NOT_APPLICABLE;
            case PASSED:
                return INTERVIEW_CONFIRM_WAITING;
        }
        throw new IllegalStateException();
    }

    private ApplicantConfirmationStatus updateByInterviewStatus(ApplicationInterviewStatus interviewStatus) {
        switch (interviewStatus) {
            case NOT_RATED:
            case NOT_APPLICABLE:
                return this;
            case FAILED:
            case TO_BE_DETERMINED:
                return INTERVIEW_CONFIRM_ACCEPTED;
            case PASSED:
                return FINAL_CONFIRM_WAITING;
        }
        throw new IllegalStateException();
    }
}
