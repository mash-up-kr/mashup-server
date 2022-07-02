package kr.mashup.branding.facade.attendance;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.attendance.reqeust.AttendanceCheckRequest;
import kr.mashup.branding.ui.attendance.response.AttendanceCheckResponse;
import kr.mashup.branding.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceFacadeService {

    private final AttendanceService attendanceService;
    private final MemberService memberService;
    private final EventService eventService;

    /**
     * 출석 체크
     */
    @Transactional(readOnly = true)
    public AttendanceCheckResponse checkAttendance(AttendanceCheckRequest req) {
        final LocalDateTime checkTime = LocalDateTime.now();

        final Member member = memberService.getOrThrowById(req.getMemberId());
        final Event event = eventService.getByIdOrThrow(req.getEventId());

        validAlreadyCheckAttendance(member, event);

        // 출석 코드를 가장 최근에 등록한 것을 가져옴
        final AttendanceCode recentCode =
            getRecentAttendanceCode(event.getAttendanceCodeList());

        // 출석 체크
        Attendance attendance = attendanceService.checkAttendance(
            member,
            event,
            getAttendanceStatus(recentCode, checkTime)
        );

        return AttendanceCheckResponse.from(attendance);
    }

    /**
     * 이미 출석 체크를 했는지 판별
     */
    private void validAlreadyCheckAttendance(Member member, Event event) {
        boolean isAlreadyCheck = attendanceService.isExist(member, event);
        if (isAlreadyCheck) {
            throw new BadRequestException(ResultCode.ATTENDANCE_ALREADY_CHECKED);
        }
    }

    /**
     * 가장 최근에 등록한 출석 코드 가져오기
     */
    private AttendanceCode getRecentAttendanceCode(
        List<AttendanceCode> attendanceCodes
    ) {
        return attendanceCodes.stream()
            .max(Comparator.comparing(AttendanceCode::getCreatedAt))
            .orElseThrow(
                () -> new BadRequestException(ResultCode.ATTENDANCE_CODE_EMPTY)
            );
    }

    /**
     * 출석 코드 정보와 출석 시간을 가지고 출석 상태 판별
     */
    private AttendanceStatus getAttendanceStatus(
        AttendanceCode attendanceCode,
        LocalDateTime checkTime
    ) {
        final boolean isInTime = DateUtil.isInTime(
            attendanceCode.getStartedAt(),
            attendanceCode.getEndedAt(),
            checkTime
        );
        if (isInTime) return AttendanceStatus.ATTENDANCE;
        return AttendanceStatus.LATE;
    }
}
