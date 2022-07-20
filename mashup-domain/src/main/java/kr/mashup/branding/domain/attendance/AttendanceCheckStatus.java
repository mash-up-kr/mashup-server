package kr.mashup.branding.domain.attendance;

public enum AttendanceCheckStatus {
    BEFORE("진행 전"),
    ACTIVE("진행 중"),
    AFTER("진행 후")
    ;

    private String description;

    AttendanceCheckStatus(String description) {
        this.description = description;
    }
}
