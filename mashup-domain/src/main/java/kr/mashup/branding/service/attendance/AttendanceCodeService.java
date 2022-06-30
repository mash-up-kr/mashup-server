package kr.mashup.branding.service.attendance;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.repository.attendancecode.AttendanceCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttendanceCodeService {

    private final AttendanceCodeRepository attendanceCodeRepository;

    @Transactional
    public AttendanceCode save(AttendanceCode attendanceCode) {
        return attendanceCodeRepository.save(attendanceCode);
    }

    @Transactional(readOnly = true)
    public void validateDup(Event event, String code) {
        boolean isExist = attendanceCodeRepository.existsByEventAndCode(event, code);
        if (isExist) {
            throw new BadRequestException(ResultCode.ATTENDANCE_CODE_DUPLICATED);
        }
    }

    @Transactional(readOnly = true)
    public AttendanceCode getOrThrow(Long eventId, String code) {
        return attendanceCodeRepository.findByEventIdAndCode(eventId, code)
            .orElseThrow(() -> new NotFoundException(ResultCode.ATTENDANCE_CODE_NOT_FOUND));
    }

}
