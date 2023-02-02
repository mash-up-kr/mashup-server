package kr.mashup.branding.ui.schedule.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.schedule.Event;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class EventResponse {

    Long eventId;

    String eventName;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    List<ContentResponse> contentList;

    public static EventResponse from(Event event) {
        return EventResponse.of(
            event.getId(),
            event.getEventName(),
            event.getStartedAt(),
            event.getEndedAt(),
            event.getContentList()
                .stream()
                .map(ContentResponse::from)
                .collect(Collectors.toList())
        );
    }
}
