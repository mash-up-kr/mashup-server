package kr.mashup.branding.service.schedule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.schedule.Content;
import kr.mashup.branding.domain.schedule.ContentsCreateDto;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.ScheduleCreateDto;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import kr.mashup.branding.domain.schedule.exception.ScheduleAlreadyPublishedException;
import kr.mashup.branding.domain.schedule.exception.ScheduleNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.repository.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public Schedule create(Generation generation, ScheduleCreateDto dto) {
        Schedule schedule = Schedule.of(generation, dto.getName(), dto.getDateRange());
        return scheduleRepository.save(schedule);
    }

    public Schedule getByIdOrThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new NotFoundException(ResultCode.SCHEDULE_NOT_FOUND));
    }

    public List<Schedule> getByGeneration(Generation generation) {
        return scheduleRepository.findByGeneration(
            generation,
            Sort.by(Sort.Direction.ASC, "startedAt")
        );
    }

    public Event addEvents(Schedule schedule, EventCreateDto dto){
        final Event event
            = Event.of(schedule, dto.getEventName(), dto.getDateRange());
        schedule.addEvent(event);
        return event;
    }


    public Content addContent(Event event, ContentsCreateDto dto) {
        final Content content
            = Content.of(event, dto.getTitle(), dto.getDesc(), dto.getStartedAt());
        event.addContent(content);
        return content;
    }

    public Schedule publishSchedule(Long scheduleId) {
        final Schedule schedule
            = scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFoundException::new);
        if(schedule.getStatus() == ScheduleStatus.ADMIN_ONLY){
            throw new ScheduleAlreadyPublishedException();
        }
        schedule.publishSchedule();

        return schedule;
    }
}
