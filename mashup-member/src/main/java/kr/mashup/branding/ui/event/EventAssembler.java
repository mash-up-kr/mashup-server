package kr.mashup.branding.ui.event;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.ui.content.ContentAssembler;
import kr.mashup.branding.ui.event.response.EventResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventAssembler {
    private final ContentAssembler contentAssembler;

    public EventResponse toEventResponse(Event event) {
        return new EventResponse(
            event.getId(),
            event.getStartedAt(),
            event.getEndedAt(),
            event.getContentList()
                .stream()
                .map(contentAssembler::toContentResponse)
                .collect(Collectors.toList()),
            event.getAttendanceCode()
        );
    }
}
