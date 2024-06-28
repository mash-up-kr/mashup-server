package kr.mashup.branding.repository.attendance;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.member.Member;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, CustomAttendanceRepository {
    boolean existsAttendanceByMemberAndEvent(Member member, Event event);

    Optional<Attendance> findByMemberAndEvent(Member member, Event event);

    List<Attendance> findAllByMember(Member member);

    void deleteByMember(Member member);

    boolean existsByEvent(Event event);
}
/**
 * Attendance 연관관계
 * many to one: member, event
 */
