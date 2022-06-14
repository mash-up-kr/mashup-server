package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.repository.member.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event getByIdOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException(ResultCode.EVENT_NOT_FOUND));
    }
}
