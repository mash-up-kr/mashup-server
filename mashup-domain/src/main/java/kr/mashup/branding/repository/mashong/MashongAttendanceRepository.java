package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.MashongAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MashongAttendanceRepository extends JpaRepository<MashongAttendance, Long> {
    List<MashongAttendance> findAllByMemberGenerationIdAndAttendanceAtBetween(Long memberGenerationId, LocalDateTime start, LocalDateTime end);
}
