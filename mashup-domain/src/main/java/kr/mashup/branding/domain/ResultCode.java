package kr.mashup.branding.domain;

public enum ResultCode {
    SUCCESS("성공"),

    // 공통
    BAD_REQUEST("요청에 오류가 있습니다."),
    UNAUTHORIZED("인증이 필요한 요청입니다."),
    FORBIDDEN("허용되지 않은 접근입니다."),
    NOT_FOUND("대상이 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),

    // applicant (지원자)
    APPLICANT_NOT_FOUND("지원자가 존재하지 않습니다."),

    // application (지원서)
    APPLICATION_NOT_FOUND("지원서가 존재하지 않습니다."),
    APPLICATION_ALREADY_SUBMITTED("지원서가 이미 제출되었습니다."),
    APPLICATION_PRIVACY_POLICY_NOT_AGREED("개인정보처리방침에 동의해야합니다."),
    APPLICATION_CONFIRMATION_UPDATE_INVALID("올바르지 않은 지원결과 확인 요청 입니다."),

    // application result (지원서 결과)
    INTERVIEW_TIME_INVALID("면접시간이 올바르지 않습니다."),

    // applicationForm (설문지)
    APPLICATION_FORM_ALREADY_EXIST("같은 팀에 이미 다른 설문지가 존재합니다."),
    APPLICATION_FORM_MODIFICATION_NOT_ALLOWED("지원서를 수정하거나 삭제할 수 없습니다."),
    APPLICATION_FORM_NOT_FOUND("설문지가 존재하지 않습니다."),

    // SMS (문자 발송)
    NOTIFICATION_NOT_FOUND("통지 정보가 없습니다."),
    NOTIFICATION_REQUEST_INVALID("잘못된 통지 요청입니다."),
    NOTIFICATION_FAILED_TO_SEND_SMS("문자 발송에 실패했습니다."),

    // Schedule (일정)
    RECRUITMENT_SCHEDULE_NOT_FOUND("채용 일정이 존재하지 않습니다."),

    // Team (팀)
    TEAM_NOT_FOUND("팀이 존재하지 않습니다."),

    // AdminMember (어드민)
    ADMIN_MEMBER_NOT_FOUND("관리자 멤버가 존재하지 않습니다."),
    ADMIN_MEMBER_SIGN_UP_REQUEST_INVALID("회원 가입 요청에 오류가 있습니다."),
    ADMIN_MEMBER_USERNAME_DUPLICATED("이미 사용중인 username 입니다."),
    ;

    private final String message;

    ResultCode(String message) {
        this.message = message;
    }
}
