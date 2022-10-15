package kr.mashup.branding.repository.attendancecode;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceCodeRepository extends JpaRepository<AttendanceCode, Long> {
}
/**
 * AttendanceCode 연관관계
 * 없음
 */