package kr.mashup.branding.domain.attendance;

public enum AttendanceStatus {
    LATE, ATTENDANCE, ABSENT;

    public static AttendanceStatus combine(
        AttendanceStatus status1,
        AttendanceStatus status2
    ) {
        if(status1 == ABSENT || status2 == ABSENT) return ABSENT;
        if(status1 == LATE || status2 == LATE) return LATE;
        return ATTENDANCE;
    }
}
