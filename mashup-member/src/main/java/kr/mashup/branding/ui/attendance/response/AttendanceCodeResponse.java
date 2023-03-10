package kr.mashup.branding.ui.attendance.response;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AttendanceCodeResponse {
    @ApiModelProperty(required = true)
    private final Long id;
    @ApiModelProperty(required = true, notes = "이벤트 ID")
    private final Long eventId;
    @ApiModelProperty(required = true, notes = "출석 코드")
    private final String code;
    @ApiModelProperty(required = true)
    private final LocalDateTime startedAt;
    @ApiModelProperty(required = true)
    private final LocalDateTime endedAt;

    public static AttendanceCodeResponse from(AttendanceCode attendanceCode) {
        return new AttendanceCodeResponse(
            attendanceCode.getId(),
            attendanceCode.getEvent().getId(),
            attendanceCode.getCode(),
            attendanceCode.getAttendanceCheckStartedAt(),
            attendanceCode.getAttendanceCheckEndedAt()
        );
    }
}
