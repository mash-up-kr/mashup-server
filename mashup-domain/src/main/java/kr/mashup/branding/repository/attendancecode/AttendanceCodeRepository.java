package kr.mashup.branding.repository.attendancecode;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceCodeRepository extends JpaRepository<AttendanceCode, Long> {
}
/**
 * AttendanceCode 연관관계
 * 없음
 */