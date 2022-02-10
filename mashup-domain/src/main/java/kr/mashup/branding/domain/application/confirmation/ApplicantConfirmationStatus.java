package kr.mashup.branding.domain.application.confirmation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicantConfirmationStatus {
    TBD("미검토"),
    INTERVIEW_CONFIRM_WAITING("면접 확인 대기중"),
    INTERVIEW_CONFIRM_ACCEPTED("면접 확인"),
    INTERVIEW_CONFIRM_REJECTED("면접 거절"),
    FINAL_CONFIRM_WAITING("최종확인대기"),
    FINAL_CONFIRM_ACCEPTED("최종확인"),
    FINAL_CONFIRM_REJECTED("최종거절"),

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
            case TBD:
            case NOT_APPLICABLE:
                throw new ConfirmationUpdateInvalidException();
        }
        throw new IllegalStateException();
    }
}
