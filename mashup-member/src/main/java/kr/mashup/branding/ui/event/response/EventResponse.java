package kr.mashup.branding.ui.event.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.ui.content.response.ContentResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventResponse {

    private final Long eventId;
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;
    private final List<ContentResponse> contentList;
    private final AttendanceCode attendanceCode;

    public static EventResponse of(Event event) {
        return new EventResponse(
            event.getId(),
            event.getStartedAt(),
            event.getEndedAt(),
            event.getContentList()
                .stream()
                .map(ContentResponse::of)
                .collect(Collectors.toList()),
            event.getAttendanceCode()
        );
    }
}
