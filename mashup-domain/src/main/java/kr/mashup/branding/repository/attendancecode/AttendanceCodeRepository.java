package kr.mashup.branding.repository.attendancecode;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceCodeRepository extends JpaRepository<AttendanceCode, Long> {

    boolean existsByCode(String code);
}
/**
 * AttendanceCode 연관관계
 * Many To One : event
 */