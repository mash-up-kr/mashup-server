package kr.mashup.branding.domain.application.progress;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationProgressStatus {
    TBD("미정"),
    WAIT_CONFIRM_INTERVIEW("면접 확인 대기중"),
    INTERVIEW_CONFIRM("면접 확인"),
    INTERVIEW_REJECT("면접 거절"),
    WAIT_FINAL_CONFIRM("최종확인대기"),
    FINAL_CONFIRM("최종확인"),
    REJECT_FINAL_CONFIRM("최종거절"),

    NOT_APPLICABLE("해당 없음");

    private final String description;

    public ApplicationProgressStatus updateFromApplicant(ApplicationProgressStatus status) {
        switch (this) {
            case WAIT_CONFIRM_INTERVIEW:
                if (status == INTERVIEW_CONFIRM || status == INTERVIEW_REJECT) {
                    return status;
                }
            case WAIT_FINAL_CONFIRM:
                if (status == FINAL_CONFIRM || status == REJECT_FINAL_CONFIRM) {
                    return status;
                }
        }
        throw new ApplicationProgressUpdateInvalidException();
    }
}
