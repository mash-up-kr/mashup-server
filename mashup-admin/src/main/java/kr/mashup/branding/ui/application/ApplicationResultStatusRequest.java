package kr.mashup.branding.ui.application;

/**
 * 지원서 결과 상태 변경요청시 사용함
 */
public enum ApplicationResultStatusRequest {
    NOT_RATED("미검토"),
    SCREENING_FAILED("서류 불합격"),
    SCREENING_TO_BE_DETERMINED("서류 보류"),
    SCREENING_PASSED("서류 합격"),
    INTERVIEW_FAILED("최종 불합격"),
    // TODO: '면접 예정' 은 있고 '면접 보류' 는 없어서 문의해보아야함
    INTERVIEW_TO_BE_DETERMINED("면접 예정"),
    INTERVIEW_PASSED("최종 합격"),
    ;

    ApplicationResultStatusRequest(String description) {
    }
}
