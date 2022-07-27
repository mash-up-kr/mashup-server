package kr.mashup.branding.ui.attendance.response;

import lombok.Getter;
import lombok.Value;

import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class PersonalAttendanceResponse {
    String memberName;
    List<AttendanceInfo> attendanceInfos;
}
