package kr.mashup.branding.service.schedule;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.*;
import kr.mashup.branding.domain.schedule.exception.CodeGenerateFailException;
import kr.mashup.branding.domain.schedule.exception.EventNotFoundException;
import kr.mashup.branding.domain.schedule.exception.ScheduleAlreadyPublishedException;
import kr.mashup.branding.domain.schedule.exception.ScheduleNotDeletableException;
import kr.mashup.branding.domain.schedule.exception.ScheduleNotFoundException;
import kr.mashup.branding.repository.attendancecode.AttendanceCodeRepository;
import kr.mashup.branding.repository.schedule.ScheduleRepository;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AttendanceCodeRepository attendanceCodeRepository;

    public Schedule create(Generation generation, ScheduleCreateDto dto) {
        Schedule schedule = Schedule.of(generation, dto.getName(), dto.getDateRange());
        return scheduleRepository.save(schedule);
    }

    public Schedule getByIdOrThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new NotFoundException(ResultCode.SCHEDULE_NOT_FOUND));
    }

    public List<Schedule> getByGeneration(Generation generation) {
        return scheduleRepository.findByGeneration(generation, Pageable.unpaged()).toList();
    }


    public Page<Schedule> getByGeneration(Generation generation, Pageable pageable) {
        return scheduleRepository
            .findByGeneration(generation, pageable);
    }

    public Event addEvents(Schedule schedule, EventCreateDto dto) {

        onlyHidingScheduleCanChanged(schedule);

        final Event event
            = Event.of(schedule, dto.getEventName(), dto.getDateRange());

        schedule.addEvent(event);

        return event;
    }

    public Event getEventOrThrow(Schedule schedule, Long eventId) {
        return schedule
            .getEventList()
            .stream()
            .filter(it -> it.getId().equals(eventId))
            .findFirst()
            .orElseThrow(EventNotFoundException::new);
    }

    public Content addContent(Event event, ContentsCreateDto dto) {
        final Schedule schedule = event.getSchedule();
        onlyHidingScheduleCanChanged(schedule);

        final Content content
            = Content.of(event, dto.getTitle(), dto.getDescription(), dto.getStartedAt());

        return content;
    }

    public Schedule publishSchedule(Schedule schedule) {

        schedule.publishSchedule();

        return schedule;
    }

    public void deleteSchedule(Schedule schedule) {
        onlyHidingScheduleCanChanged(schedule);
        passedScheduleMustNotBeDeleted(schedule);

        scheduleRepository.delete(schedule);
    }

    public Schedule updateSchedule(Schedule schedule, Generation generation, ScheduleCreateDto scheduleCreateDto) {
        onlyHidingScheduleCanChanged(schedule);

        schedule.changeName(scheduleCreateDto.getName());
        schedule.changeGeneration(generation);
        schedule.changeDate(scheduleCreateDto.getDateRange().getStart(), scheduleCreateDto.getDateRange().getEnd());

        return schedule;
    }

    private void passedScheduleMustNotBeDeleted(Schedule schedule) {
        if (schedule.getStartedAt().isBefore(LocalDateTime.now())) {
            throw new ScheduleNotDeletableException();
        }
    }

    public void hideSchedule(Schedule schedule) {
        schedule.hide();
    }

    private void onlyHidingScheduleCanChanged(Schedule schedule) {
        if (schedule.getStatus() == ScheduleStatus.PUBLIC) {
            throw new ScheduleAlreadyPublishedException();
        }
    }

    public AttendanceCode addAttendanceCode(Event event, DateRange codeValidRequestTime) {

        String code = null;

        boolean isCreated = false;

        for (int i = 0; i < 3; i++) {
            code = UUID.randomUUID().toString().substring(0, 5);
            boolean existsByCode = attendanceCodeRepository.existsByCode(code);
            if (!existsByCode) {
                isCreated = true;
                break;
            }
        }
        if (!isCreated) {
            throw new CodeGenerateFailException();
        }

        final AttendanceCode attendanceCode
            = event.addAttendanceCode(code, codeValidRequestTime);

        return attendanceCode;
    }

    public List<Schedule> findAllByIsCounted(boolean isCounted) {
        return scheduleRepository.findAllByIsCounted(isCounted);
    }

    public Schedule findByStartDate(LocalDate startDate) {
        return scheduleRepository.retrieveByStartDate(startDate)
                .orElseThrow(ScheduleNotFoundException::new);
    }

}
