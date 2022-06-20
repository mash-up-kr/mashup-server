package kr.mashup.branding.service.event;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.repository.member.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public Event getByIdOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException(ResultCode.EVENT_NOT_FOUND));
    }
}
