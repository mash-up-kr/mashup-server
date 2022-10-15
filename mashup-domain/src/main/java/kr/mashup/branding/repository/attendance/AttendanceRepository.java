package kr.mashup.branding.repository.attendance;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsAttendanceByMemberAndEvent(Member member, Event event);
    Optional<Attendance> findByMemberAndEvent(Member member, Event event);
    List<Attendance> findAllByMember(Member member);
}
/**
 * Attendance 연관관계
 * many to one: member, event
 */