package kr.mashup.branding.domain.application.progress;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationProgressStatus {
    TBD("미정"),
    INTERVIEW_CONFIRM_WAITING("면접 확인 대기중"),
    INTERVIEW_CONFIRM_ACCEPTED("면접 확인"),
    INTERVIEW_CONFIRM_REJECTED("면접 거절"),
    FINAL_CONFIRM_WAITING("최종확인대기"),
    FINAL_CONFIRM_ACCEPTED("최종확인"),
    FINAL_CONFIRM_REJECTED("최종거절"),

    NOT_APPLICABLE("해당 없음");

    private final String description;

    public ApplicationProgressStatus updateFromApplicant(ApplicationProgressStatus status) {
        switch (this) {
            case INTERVIEW_CONFIRM_WAITING:
                if (status == INTERVIEW_CONFIRM_ACCEPTED || status == INTERVIEW_CONFIRM_REJECTED) {
                    return status;
                }
            case FINAL_CONFIRM_WAITING:
                if (status == FINAL_CONFIRM_ACCEPTED || status == FINAL_CONFIRM_REJECTED) {
                    return status;
                }
            case TBD:
            case INTERVIEW_CONFIRM_ACCEPTED:
            case INTERVIEW_CONFIRM_REJECTED:
            case NOT_APPLICABLE:
            case FINAL_CONFIRM_ACCEPTED:
            case FINAL_CONFIRM_REJECTED:
                throw new ApplicationProgressUpdateInvalidException();
        }
        throw new ApplicationProgressUpdateInvalidException();
    }
}
