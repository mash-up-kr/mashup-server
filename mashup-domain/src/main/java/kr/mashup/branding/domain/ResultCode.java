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
    APPLICATION_CREATION_REQUEST_INVALID("지원서 생성 요청이 올바르지 않습니다."),
    APPLICATION_ALREADY_SUBMITTED("이미 제출한 지원서가 존재합니다."),
    APPLICATION_PRIVACY_POLICY_NOT_AGREED("개인정보 처리방침에 동의해야합니다."),
    APPLICATION_CONFIRMATION_UPDATE_INVALID("올바르지 않은 지원결과 확인 요청 입니다."),
    APPLICATION_MODIFICATION_NOT_ALLOWED("지원서 제출 기간이 아닙니다."),
    APPLICATION_SUBMIT_REQUEST_INVALID("지원서 제출 요청에 올바르지 않은 값이 있습니다."),
    APPLICATION_NO_ACCESS("해당 지원서에 대한 접근 권한이 없습니다."),

    // application result (지원서 결과)
    INTERVIEW_TIME_INVALID("면접시간이 올바르지 않습니다."),

    // applicationForm (설문지)
    APPLICATION_FORM_ALREADY_EXIST("같은 팀에 이미 다른 설문지가 존재합니다."),
    APPLICATION_FORM_MODIFICATION_NOT_ALLOWED("모집 시작시각 이후에는 설문지를 수정하거나 삭제할 수 없습니다"),
    APPLICATION_FORM_NOT_FOUND("설문지가 존재하지 않습니다."),
    APPLICATION_FORM_DELETE_NOT_ALLOWED("생성된 지원서가 있어 설문지를 삭제 할 수 없습니다."),
    APPLICATION_FORM_NAME_DUPLICATED("설문지 이름이 이미 사용중입니다."),

    // SMS (문자 발송)
    NOTIFICATION_NOT_FOUND("통지 정보가 없습니다."),
    NOTIFICATION_REQUEST_INVALID("잘못된 통지 요청입니다."),
    NOTIFICATION_FAILED_TO_SEND_SMS("문자 발송에 실패했습니다."),

    // Schedule (일정)
    RECRUITMENT_SCHEDULE_NOT_FOUND("채용 일정이 존재하지 않습니다."),
    RECRUITMENT_SCHEDULE_DUPLICATED("이미 사용중인 일정 이름입니다."),

    // Team (팀)
    TEAM_NOT_FOUND("팀이 존재하지 않습니다."),

    // AdminMember (어드민)
    ADMIN_MEMBER_NOT_FOUND("관리자 멤버가 존재하지 않습니다."),
    ADMIN_MEMBER_SIGN_UP_REQUEST_INVALID("회원 가입 요청에 오류가 있습니다."),
    ADMIN_MEMBER_USERNAME_DUPLICATED("이미 사용중인 username 입니다."),
    ADMIN_MEMBER_LOGIN_FAILED("username 또는 password 가 올바르지 않습니다."),
    ADMIN_MEMBER_NO_ACCESS_TEAM("접근 권한이 없는 팀입니다."),
    ADMIN_MEMBER_NO_UPDATE_PERMISSION("수정 권한이 없는 관리자 멤버입니다."),

    // Member (회원)
    MEMBER_DUPLICATED_IDENTIFICATION("중복되는 아이디입니다."),
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다."),
    MEMBER_NOT_MATCH_PASSWORD("비밀번호가 일치하지 않습니다."),
    MEMBER_INVALID_INVITE("잘못된 가입 코드입니다."),
    MEMBER_INVALID_EMPTY_NAME("잘못된 이름입니다."),
    MEMBER_PENDING_STATUS("가입 승인 대기중입니다."),


    // Platform (플랫폼)
    INVALID_PLATFORM_NAME("잘못된 플랫폼 이름입니다."),
    // Event (일정)
    EVENT_NOT_FOUND("일정이 존재하지 않습니다."),

    // Schedule (스케줄)
    SCHEDULE_NOT_FOUND("스케줄이 존재하지 않습니다."),

    // Schedule (일정 내용)
    CONTENT_NOT_FOUND("일정 내용이 존재하지 않습니다."),

    // Generation (기수)
    GENERATION_NOT_FOUND("기수가 존재하지 않습니다."),
    MEMBER_GENERATION_NOT_FOUND("사용자의 기수 정보가 존재하지 않습니다."),

    // AttendanceCode (출석 코드)
    ATTENDANCE_CODE_DUPLICATED("이미 사용된 코드입니다."),
    ATTENDANCE_CODE_NOT_FOUND("출석 코드가 존재하지 않습니다."),
    ATTENDANCE_CODE_INVALID("유효하지 않은 출석 코드 입니다."),

    // Attendance (출석)
    ATTENDANCE_NOT_FOUND("출석 정보가 존재하지 않습니다."),
    ATTENDANCE_ALREADY_CHECKED("이미 출석 체크를 했습니다."),
    ATTENDANCE_TIME_BEFORE("아직 출석 체크 시간이 아닙니다."),
    ATTENDANCE_TIME_OVER("출석 체크 시간이 지났습니다."),

    // Invite (초대 코드)
    INVITE_NOT_FOUND("초대 코드가 존재하지 않습니다.");


    private final String message;

    ResultCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
