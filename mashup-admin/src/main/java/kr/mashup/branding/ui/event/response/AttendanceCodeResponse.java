package kr.mashup.branding.ui.event.response;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AttendanceCodeResponse {
    private final Long id;
    private final Long eventId;
    private final String code;
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;

    public static AttendanceCodeResponse from(AttendanceCode attendanceCode) {
        return new AttendanceCodeResponse(
            attendanceCode.getId(),
            attendanceCode.getEventId(),
            attendanceCode.getCode(),
            attendanceCode.getStartedAt(),
            attendanceCode.getEndedAt()
        );
    }
}
