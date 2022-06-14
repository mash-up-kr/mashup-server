package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.repository.member.AttendanceCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceCodeService {

    private final AttendanceCodeRepository attendanceCodeRepository;

    public void save(AttendanceCode attendanceCode) {
        attendanceCodeRepository.save(attendanceCode);
    }

    public void validateDup(Event event,String code) {
        boolean isExist = attendanceCodeRepository.existsByEventAndCode(event, code);
        if(isExist) {
            throw new BadRequestException(ResultCode.ATTENDANCE_CODE_DUPLICATED);
        }
    }
}
