package kr.mashup.branding.ui.event.response;

import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.ui.attendance.response.AttendanceCodeResponse;
import kr.mashup.branding.ui.content.response.ContentResponse;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Value(staticConstructor = "of")
public class EventResponse {

    Long eventId;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    List<ContentResponse> contentList;

    List<AttendanceCodeResponse> attendanceCode;

    public static EventResponse from(Event event) {
        return EventResponse.of(
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
