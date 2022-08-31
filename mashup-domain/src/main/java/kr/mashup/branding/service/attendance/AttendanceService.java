package kr.mashup.branding.service.attendance;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.repository.attendance.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public boolean isExist(Member member, Event event) {
        return attendanceRepository.existsAttendanceByMemberAndEvent(member, event);
    }

    public Attendance getOrThrow(Member member, Event event) {
        return attendanceRepository.findByMemberAndEvent(member, event)
            .orElseThrow(() -> new NotFoundException(ResultCode.ATTENDANCE_NOT_FOUND));
    }

    public Attendance checkAttendance(
        Member member,
        Event event,
        AttendanceStatus status
    ) {
        return attendanceRepository.save(Attendance.of(member, status, event));
    }

    public AttendanceStatus getAttendanceStatusByMemberAndStartedEvents(
        Member member,
        List<Event> startedEvents
    ) {
        if(startedEvents.isEmpty()) return AttendanceStatus.ABSENT;

        return startedEvents.stream()
            .map(event ->
                attendanceRepository.findByMemberAndEvent(member, event)
                    .orElse(null))
            .map(attendance -> {
                if(attendance == null) return AttendanceStatus.ABSENT;
                return attendance.getStatus();
            })
            .reduce(
                AttendanceStatus.ATTENDANCE,
                AttendanceStatus::combine
            );
    }
}
