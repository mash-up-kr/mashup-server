package kr.mashup.branding.service.schedule;

import java.time.LocalDateTime;
import java.util.List;

import kr.mashup.branding.domain.schedule.Content;
import kr.mashup.branding.domain.schedule.ContentsCreateDto;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.ScheduleCreateDto;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import kr.mashup.branding.domain.schedule.exception.ScheduleAlreadyPublishedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return scheduleRepository.findByGeneration(generation,Pageable.unpaged());
    }

    public Page<Schedule> getByGeneration(Generation generation, Pageable pageable) {
        return scheduleRepository
            .findByGeneration(generation, pageable);
    }

    public Event addEvents(Schedule schedule, EventCreateDto dto){
        if(schedule.getStatus() == ScheduleStatus.PUBLIC){

        }
        final Event event
            = Event.of(schedule, dto.getEventName(), dto.getDateRange());
        schedule.addEvent(event);
        return event;
    }


    public Content addContent(Event event, ContentsCreateDto dto) {
        final Schedule schedule = event.getSchedule();
        if(schedule.getStatus() == ScheduleStatus.PUBLIC){

        }
        final Content content
            = Content.of(event, dto.getTitle(), dto.getDesc(), dto.getStartedAt());
        event.addContent(content);
        return content;
    }

    public Schedule publishSchedule(Schedule schedule) {
        schedule.publishSchedule();
        return schedule;
    }

    public void deleteSchedule(Schedule schedule) {
        if(schedule.getStatus() == ScheduleStatus.PUBLIC){

        }
        if(schedule.getStartedAt().isBefore(LocalDateTime.now())){

        }
        scheduleRepository.delete(schedule);
    }

    public void hideSchedule(Schedule schedule) {
        schedule.hide();
    }
}
