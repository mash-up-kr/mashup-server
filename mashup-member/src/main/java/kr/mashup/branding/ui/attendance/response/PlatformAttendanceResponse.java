package kr.mashup.branding.ui.attendance.response;

import kr.mashup.branding.domain.attendance.AttendanceStatus;
import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class PlatformAttendanceResponse {
    Platform platform;
    List<MemberInfo> members;

    @Getter
    @Value(staticConstructor = "of")
    public static class MemberInfo {
        String name;
        List<AttendanceInfo> attendanceInfos;
    }

    @Getter
    @Value(staticConstructor = "of")
    public static class AttendanceInfo {
        AttendanceStatus status;
        LocalDateTime attendanceAt;
    }
}
