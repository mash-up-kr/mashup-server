package kr.mashup.branding.ui.attendance.response;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class AttendanceCheckResponse {
    @ApiModelProperty(required = true)
    AttendanceStatus status;

    public static AttendanceCheckResponse from(Attendance attendance) {
        return AttendanceCheckResponse.of(attendance.getStatus());
    }
}
