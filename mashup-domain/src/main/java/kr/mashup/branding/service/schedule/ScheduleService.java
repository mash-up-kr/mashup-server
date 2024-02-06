package kr.mashup.branding.service.schedule;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.*;
import kr.mashup.branding.domain.schedule.exception.*;
import kr.mashup.branding.repository.attendancecode.AttendanceCodeRepository;
import kr.mashup.branding.repository.schedule.ScheduleRepository;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AttendanceCodeRepository attendanceCodeRepository;

    public Schedule create(Generation generation, ScheduleCreateDto dto) {
        Location location = new Location(dto.getLatitude(), dto.getLongitude(), dto.getAddress(), dto.getPlaceName());
        Schedule schedule = Schedule.of(generation, dto.getName(), dto.getDateRange(), location);
        return scheduleRepository.save(schedule);
    }

    public Schedule getByIdOrThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new NotFoundException(ResultCode.SCHEDULE_NOT_FOUND));
    }

    public Schedule getByIdAndStatusOrThrow(Long scheduleId, ScheduleStatus status) {
        return scheduleRepository.findByIdAndStatus(scheduleId, status)
                .orElseThrow(() -> new NotFoundException(ResultCode.SCHEDULE_NOT_FOUND));
    }

    public List<Schedule> getByGenerationAndStatus(Generation generation, ScheduleStatus status) {
        return scheduleRepository.findByGenerationAndStatusOrderByStartedAtAsc(generation, status);
    }

    public Page<Schedule> getByGeneration(Generation generation, String searchWord, ScheduleStatus status, Pageable pageable) {
        return scheduleRepository
            .retrieveByGeneration(generation, searchWord, status, pageable);
    }

    public Event addEvents(Schedule schedule, EventCreateDto dto) {

        onlyHidingScheduleCanChanged(schedule);

        final String code = generateAttendanceCodeString();
        final Event event
            = Event.of(schedule, dto.getEventName(), dto.getDateRange(), code);

        schedule.addEvent(event);

        return event;
    }

    public void updateAttendanceTime(Event event, DateRange attendanceTime, DateRange lateTime){
        final AttendanceCode attendanceCode = event.getAttendanceCode();
        attendanceCode.changeAttendanceTime(attendanceTime);
        attendanceCode.changeLateTime(lateTime);
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

        Location location = new Location(scheduleCreateDto.getLatitude(), scheduleCreateDto.getLongitude(), scheduleCreateDto.getAddress(), scheduleCreateDto.getPlaceName());
        schedule.changeLocation(location);

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


    public List<Schedule> findEndedScheduleByIsCounted(boolean isCounted) {
        return scheduleRepository.findAllByIsCountedAndEndedAtIsBefore(isCounted, LocalDateTime.now());
    }

    public Schedule findByStartDate(LocalDate startDate) {
        return scheduleRepository.retrieveByStartDate(startDate)
                .orElseThrow(ScheduleNotFoundException::new);
    }

    private String generateAttendanceCodeString(){

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

        return code;
    }

}
