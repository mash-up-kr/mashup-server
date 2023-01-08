package kr.mashup.branding.repository.attendancecode;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceCodeRepository extends JpaRepository<AttendanceCode, Long> {

    boolean existsByCode(String code);

    Optional<AttendanceCode> findByCode(String code);
}
/**
 * AttendanceCode 연관관계
 * Many To One : event
 */