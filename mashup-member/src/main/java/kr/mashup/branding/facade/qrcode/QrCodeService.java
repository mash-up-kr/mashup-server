package kr.mashup.branding.facade.qrcode;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.service.attendance.AttendanceCodeService;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.ui.qrcode.request.QrCreateRequest;
import kr.mashup.branding.ui.qrcode.response.QrCodeResponse;
import kr.mashup.branding.ui.qrcode.response.QrCreateResponse;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.QrGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QrCodeService {
    private final EventService eventService;
    private final AttendanceCodeService attendanceCodeService;

    // QR 코드 생성
    @Transactional
    public QrCreateResponse generate(QrCreateRequest req) {
        final Event event = eventService.getByIdOrThrow(req.getEventId());
        final DateRange period = DateRange.of(req.getStart(), req.getEnd());

        attendanceCodeService.save(
            AttendanceCode.of(event, req.getCode(), period)
        );

        final String qrCode = QrGenerator.generate(req.getCode());

        return QrCreateResponse.of(qrCode);
    }

    @Transactional(readOnly = true)
    public QrCodeResponse getQrCode(Long eventId) {
        final Event event = eventService.getByIdOrThrow(eventId);
        final AttendanceCode attendanceCode = event.getAttendanceCode();

        if(attendanceCode == null) {
            throw new BadRequestException(ResultCode.ATTENDANCE_CODE_NOT_FOUND);
        }

        final String qrCodeUrl = QrGenerator.generate(attendanceCode.getCode());

        return QrCodeResponse.of(qrCodeUrl);
    }
}
