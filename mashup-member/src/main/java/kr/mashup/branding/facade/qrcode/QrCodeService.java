package kr.mashup.branding.facade.qrcode;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.ui.qrcode.response.QrCodeResponse;
import kr.mashup.branding.util.QrGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QrCodeService {
    private final EventService eventService;

    @Transactional(readOnly = true)
    public QrCodeResponse getQrCode(Long eventId) {
        final Event event = eventService.getByIdOrThrow(eventId);
        final AttendanceCode attendanceCode = event.getAttendanceCode();

        if (attendanceCode == null) {
            throw new BadRequestException(ResultCode.ATTENDANCE_CODE_NOT_FOUND);
        }

        final String qrCodeUrl = QrGenerator.generate(attendanceCode.getCode());

        return QrCodeResponse.of(qrCodeUrl);
    }
}
