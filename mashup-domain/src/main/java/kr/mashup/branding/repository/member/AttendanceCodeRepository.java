package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceCodeRepository extends JpaRepository<AttendanceCode, Long> {
    boolean existsByEventAndCode(Event event, String code);
}
