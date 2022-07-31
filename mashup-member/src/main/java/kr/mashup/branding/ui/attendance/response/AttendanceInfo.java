package kr.mashup.branding.ui.attendance.response;

import kr.mashup.branding.domain.attendance.AttendanceStatus;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Getter
@Value(staticConstructor = "of")
public class AttendanceInfo {
    AttendanceStatus status;
    LocalDateTime attendanceAt;
}
