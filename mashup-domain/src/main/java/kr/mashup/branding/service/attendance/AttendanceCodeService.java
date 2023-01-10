package kr.mashup.branding.service.attendance;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.attendance.AttendanceCodeNotFoundException;
import kr.mashup.branding.repository.attendancecode.AttendanceCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceCodeService {

    private final AttendanceCodeRepository attendanceCodeRepository;

    public AttendanceCode save(final AttendanceCode attendanceCode) {
        return attendanceCodeRepository.save(attendanceCode);
    }

    public AttendanceCode getByCodeOrThrow(String code) {
        return attendanceCodeRepository.findByCode(code).orElseThrow(AttendanceCodeNotFoundException::new);
    }

    public List<AttendanceCode> findByStartedAtLeftOpenBetween(LocalDateTime from, LocalDateTime to) {
        return attendanceCodeRepository.findAllByStartedAtGreaterThanAndStartedAtLessThanEqual(from, to);
    }
    public List<AttendanceCode> findAllByEndedAtLeftOpenBetween(LocalDateTime from, LocalDateTime to) {
        return attendanceCodeRepository.findAllByEndedAtGreaterThanAndEndedAtLessThanEqual(from, to);
    }
}
