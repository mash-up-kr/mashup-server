package kr.mashup.branding.ui.event.response;

import java.time.LocalDateTime;
import java.util.List;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.ui.content.response.ContentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventResponse {

    private Long eventId;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private List<ContentResponse> contentList;

    private AttendanceCode attendanceCode;
}
