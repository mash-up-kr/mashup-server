package kr.mashup.branding.repository.attendance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.member.Member;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, CustomAttendanceRepository{
    boolean existsAttendanceByMemberAndEvent(Member member, Event event);
    Optional<Attendance> findByMemberAndEvent(Member member, Event event);
}
