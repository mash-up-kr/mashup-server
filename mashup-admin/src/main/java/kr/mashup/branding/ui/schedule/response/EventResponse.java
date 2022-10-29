package kr.mashup.branding.ui.schedule.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.ui.content.response.ContentResponse;
import kr.mashup.branding.ui.event.response.AttendanceCodeResponse;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class EventResponse {

    Long eventId;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    AttendanceCodeResponse attendanceCode;

    List<ContentResponse> contentList;

    public static EventResponse from(Event event) {
        return EventResponse.of(
            event.getId(),
            event.getStartedAt(),
            event.getEndedAt(),
            AttendanceCodeResponse.from(event.getAttendanceCode()),
            event.getContentList()
                .stream()
                .map(ContentResponse::from)
                .collect(Collectors.toList())
        );
    }
}
