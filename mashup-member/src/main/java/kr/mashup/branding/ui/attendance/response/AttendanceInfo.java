package kr.mashup.branding.ui.attendance.response;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Getter
@Value(staticConstructor = "of")
public class AttendanceInfo {
    @ApiModelProperty(required = true)
    AttendanceStatus status;
    @ApiModelProperty(notes = "결석일 경우 null")
    LocalDateTime attendanceAt;
}
