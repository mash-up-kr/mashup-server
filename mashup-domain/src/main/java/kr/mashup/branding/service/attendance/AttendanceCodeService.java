package kr.mashup.branding.service.attendance;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.repository.attendancecode.AttendanceCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceCodeService {

    private final AttendanceCodeRepository attendanceCodeRepository;

    public AttendanceCode save(AttendanceCode attendanceCode) {
        return attendanceCodeRepository.save(attendanceCode);
    }
}
