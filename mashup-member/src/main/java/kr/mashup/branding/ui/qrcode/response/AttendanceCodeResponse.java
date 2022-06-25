package kr.mashup.branding.ui.qrcode.response;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AttendanceCodeResponse {
    private final Long id;
    private final String code;
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;

    public static AttendanceCodeResponse from(AttendanceCode attendanceCode) {
        return new AttendanceCodeResponse(
            attendanceCode.getId(),
            attendanceCode.getCode(),
            attendanceCode.getStartedAt(),
            attendanceCode.getEndedAt()
        );
    }
}
