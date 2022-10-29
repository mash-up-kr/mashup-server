package kr.mashup.branding.facade.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.event.request.EventCreateRequest;
import kr.mashup.branding.ui.schedule.response.EventResponse;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventFacadeService {

    private final EventService eventService;
    private final ScheduleService scheduleService;

    @Transactional
    public EventResponse create(EventCreateRequest req) {
        final Schedule schedule = scheduleService.getByIdOrThrow(req.getScheduleId());
        final DateRange dateRange = DateRange.of(req.getStartedAt(), req.getEndedAt());

        final Event event = eventService.save(Event.of(schedule, dateRange));

        return EventResponse.from(event);
    }
}
