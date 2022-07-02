package kr.mashup.branding.facade.qrcode;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.service.attendance.AttendanceCodeService;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.ui.qrcode.request.QrCreateRequest;
import kr.mashup.branding.ui.qrcode.response.QrCheckResponse;
import kr.mashup.branding.ui.qrcode.response.QrCreateResponse;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
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
        Event event = eventService.getByIdOrThrow(req.getEventId());
        DateRange period = DateRange.of(req.getStart(), req.getEnd());

        attendanceCodeService.save(
            AttendanceCode.of(event, req.getCode(), period)
        );

        String qrCode = QrGenerator.generate(req.getCode());

        return QrCreateResponse.of(qrCode);
    }

    // QR 코드 확인
    public QrCheckResponse isAvailableCode(
        Long eventId,
        String code,
        LocalDateTime checkTime
    ) {
        AttendanceCode attendanceCode =
            attendanceCodeService.getOrThrow(eventId, code);

        boolean isAvailable = DateUtil.isInTime(
            attendanceCode.getStartedAt(),
            attendanceCode.getEndedAt(),
            checkTime
        );

        return QrCheckResponse.of(isAvailable);
    }
}
