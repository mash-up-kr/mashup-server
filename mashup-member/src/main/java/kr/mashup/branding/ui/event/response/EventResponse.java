package kr.mashup.branding.ui.event.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.ui.content.response.ContentResponse;
import kr.mashup.branding.ui.attendance.response.AttendanceCodeResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EventResponse {

    private final Long eventId;
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;
    private final List<ContentResponse> contentList;
    private final List<AttendanceCodeResponse> attendanceCode;

    public static EventResponse from(Event event) {
        return new EventResponse(
            event.getId(),
            event.getStartedAt(),
            event.getEndedAt(),
            event.getContentList()
                .stream()
                .map(ContentResponse::from)
                .collect(Collectors.toList()),
            event.getAttendanceCodeList()
                .stream()
                .map(AttendanceCodeResponse::from)
                .collect(Collectors.toList())
        );
    }
}
