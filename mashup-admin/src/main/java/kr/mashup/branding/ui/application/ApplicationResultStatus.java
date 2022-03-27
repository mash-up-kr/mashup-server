package kr.mashup.branding.ui.application;

import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * 지원서 결과 상태 변경요청시 사용함
 */
@Slf4j
public enum ApplicationResultStatus {
    NOT_RATED("미검토"),
    SCREENING_FAILED("서류 불합격"),
    SCREENING_TO_BE_DETERMINED("서류 보류"),
    SCREENING_PASSED("서류 합격"),
    INTERVIEW_FAILED("최종 불합격"),
    INTERVIEW_TO_BE_DETERMINED("최종 보류"),
    INTERVIEW_PASSED("최종 합격"),
    ;

    ApplicationResultStatus(String description) {
    }

    public static ApplicationResultStatus of(
        ApplicationScreeningStatus screeningStatus,
        ApplicationInterviewStatus interviewStatus
    ) {
        if (interviewStatus == ApplicationInterviewStatus.PASSED) {
            return INTERVIEW_PASSED;
        }
        if (interviewStatus == ApplicationInterviewStatus.FAILED) {
            return INTERVIEW_FAILED;
        }
        if (screeningStatus == ApplicationScreeningStatus.PASSED) {
            return SCREENING_PASSED;
        }
        if (screeningStatus == ApplicationScreeningStatus.TO_BE_DETERMINED) {
            return SCREENING_TO_BE_DETERMINED;
        }
        if (screeningStatus == ApplicationScreeningStatus.FAILED) {
            return SCREENING_FAILED;
        }
        if (screeningStatus == ApplicationScreeningStatus.NOT_RATED) {
            return NOT_RATED;
        }
        log.error("Failed to parse ApplicationResultStatus. screeningStatus: {}, interviewStatus: {}",
            screeningStatus, interviewStatus);
        return null;
    }
}
