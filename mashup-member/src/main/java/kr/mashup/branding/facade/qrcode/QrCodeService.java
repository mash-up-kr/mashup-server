package kr.mashup.branding.facade.qrcode;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.service.attendanceCode.AttendanceCodeService;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import kr.mashup.branding.util.QrGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QrCodeService {
    private final EventService eventService;
    private final AttendanceCodeService attendanceCodeService;

    // QR 코드 생성
    public String generate(
        Long eventId,
        String code,
        LocalDateTime start,
        LocalDateTime end
    ) {
        Event event = eventService.getByIdOrThrow(eventId);

        attendanceCodeService.validateDup(event, code);

        DateRange period = DateRange.of(start, end);
        AttendanceCode attendanceCode = AttendanceCode.of(event, code, period);
        attendanceCodeService.save(attendanceCode);

        return QrGenerator.generate(code);
    }

    // QR 코드 확인
    public boolean isAvailableCode(
        Long eventId,
        String code,
        LocalDateTime checkTime
    ) {
        AttendanceCode attendanceCode =
            attendanceCodeService.getOrThrow(eventId, code);

        return DateUtil.isInTime(
            attendanceCode.getStartedAt(),
            attendanceCode.getEndedAt(),
            checkTime
        );
    }
}
