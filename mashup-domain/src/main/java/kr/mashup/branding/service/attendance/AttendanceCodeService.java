package kr.mashup.branding.service.attendance;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.BadRequestException;
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

    public void validateDup(Event event, String code) {
        boolean isExist = attendanceCodeRepository.existsByEventAndCode(event, code);
        if (isExist) {
            throw new BadRequestException(ResultCode.ATTENDANCE_CODE_DUPLICATED);
        }
    }

    public AttendanceCode getOrThrow(Long eventId, String code) {
        return attendanceCodeRepository.findByEventIdAndCode(eventId, code)
            .orElseThrow(() -> new NotFoundException(ResultCode.ATTENDANCE_CODE_NOT_FOUND));
    }

}
