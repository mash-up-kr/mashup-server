package kr.mashup.branding.facade.attendance;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.AttendanceCheckStatus;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.attendance.reqeust.AttendanceCheckRequest;
import kr.mashup.branding.ui.attendance.response.AttendanceCheckResponse;
import kr.mashup.branding.ui.attendance.response.PlatformAttendanceResponse;
import kr.mashup.branding.ui.attendance.response.TotalAttendanceResponse;
import kr.mashup.branding.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AttendanceFacadeService {

    private final AttendanceService attendanceService;
    private final MemberService memberService;
    private final EventService eventService;
    private final ScheduleService scheduleService;

    /**
     * 출석 체크
     */
    @Transactional
    public AttendanceCheckResponse checkAttendance(AttendanceCheckRequest req) {
        final LocalDateTime checkTime = LocalDateTime.now();

        final Member member = memberService.getOrThrowById(req.getMemberId());
        final Event event = eventService.getByIdOrThrow(req.getEventId());
        final AttendanceCode attendanceCode = event.getAttendanceCode();

        if (attendanceCode == null) {
            throw new BadRequestException(ResultCode.ATTENDANCE_CODE_NOT_FOUND);
        }
        validEventTime(event.getStartedAt(), event.getEndedAt(), checkTime);
        validAlreadyCheckAttendance(member, event);

        // 출석 체크
        Attendance attendance = attendanceService.checkAttendance(
            member,
            event,
            getAttendanceStatus(attendanceCode, checkTime)
        );

        return AttendanceCheckResponse.from(attendance);
    }

    private void validEventTime(
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        LocalDateTime time
    ) {
        final boolean isActive = DateUtil.isInTime(startedAt, endedAt, time);
        if (!isActive) {
            throw new BadRequestException(ResultCode.ATTENDANCE_TIME_OVER);
        }
    }

    /**
     * 이미 출석 체크를 했는지 판별
     */
    private void validAlreadyCheckAttendance(Member member, Event event) {
        final boolean isAlreadyCheck = attendanceService.isExist(member, event);
        if (isAlreadyCheck) {
            throw new BadRequestException(ResultCode.ATTENDANCE_ALREADY_CHECKED);
        }
    }

    /**
     * 출석 코드 정보와 출석 시간을 가지고 출석 상태 판별
     */
    private AttendanceStatus getAttendanceStatus(
        AttendanceCode attendanceCode,
        LocalDateTime checkTime
    ) {
        final boolean isAttendance = DateUtil.isInTime(
            attendanceCode.getStartedAt(),
            attendanceCode.getEndedAt(),
            checkTime
        );
        final boolean isLate = DateUtil.isInTime(
            attendanceCode.getEndedAt(),
            attendanceCode.getEndedAt().plusMinutes(10),
            checkTime
        );

        if (isAttendance) return AttendanceStatus.ATTENDANCE;
        if (isLate) return AttendanceStatus.LATE;
        throw new BadRequestException(ResultCode.ATTENDANCE_TIME_OVER);
    }

    /**
     * 플랫폼별 전체 출석현황 조회
     */
    @Transactional(readOnly = true)
    public TotalAttendanceResponse getTotalAttendance(Long scheduleId) {
        final LocalDateTime now = LocalDateTime.now();
        final Schedule schedule = scheduleService.getByIdOrThrow(scheduleId);
        final Pair<Event, Integer> eventInfo =
            getActiveEventInfo(schedule.getEventList(), now);
        final Event event = eventInfo.getLeft();
        final Integer eventNum = eventInfo.getRight();
        final AttendanceCheckStatus attendanceCheckStatus =
            getAttendanceCheckStatus(event.getAttendanceCode(), now);

        final Map<Platform, Long> totalCountGroupByPlatform =
            Arrays.stream(Platform.values()).collect(
                Collectors.toMap(
                    platform -> platform,
                    memberService::getTotalCountByPlatform
                )
            );

        final Map<Pair<Platform, AttendanceStatus>, Long> attendanceResult =
            attendanceService.getAllByEvent(event).stream().collect(
                Collectors.groupingBy(
                    this::getGroupKeyOfPlatformAndStatus,
                    Collectors.counting()
                )
            );

        final List<TotalAttendanceResponse.PlatformInfo> platformInfos =
            totalCountGroupByPlatform.entrySet().stream()
                .map(entry -> {
                    Platform platform = entry.getKey();
                    Long totalCount = entry.getValue();
                    Long attendanceCount = attendanceResult.get(
                        Pair.of(platform, AttendanceStatus.ATTENDANCE)
                    );
                    Long lateCount = attendanceResult.get(
                        Pair.of(platform, AttendanceStatus.LATE)
                    );
                    return TotalAttendanceResponse.PlatformInfo.of(
                        platform,
                        totalCount,
                        attendanceCount,
                        lateCount
                    );
                })
                .collect(Collectors.toList());

        return TotalAttendanceResponse.of(
            platformInfos,
            eventNum,
            attendanceCheckStatus);
    }

    private AttendanceCheckStatus getAttendanceCheckStatus(
        AttendanceCode attendanceCode,
        LocalDateTime now
    ) {
        if(now.isBefore(attendanceCode.getStartedAt()))
            return AttendanceCheckStatus.BEFORE;

        if(now.isAfter(attendanceCode.getEndedAt().plusMinutes(10)))
            return AttendanceCheckStatus.AFTER;

        return AttendanceCheckStatus.ACTIVE;
    }

    private Pair<Event, Integer> getActiveEventInfo(
        List<Event> events,
        LocalDateTime now
    ) {
        int eventNum = 1;
        for (Event event : events) {
            final boolean isIn = DateUtil.isInTime(
                event.getStartedAt(),
                event.getEndedAt(),
                now
            );
            if (isIn) return Pair.of(event, eventNum);
            eventNum++;
        }
        throw new BadRequestException(ResultCode.EVENT_NOT_FOUND);
    }

    private Pair<Platform, AttendanceStatus> getGroupKeyOfPlatformAndStatus(
        Attendance attendance
    ) {
        return new ImmutablePair<>(
            attendance.getMember().getPlatform(),
            attendance.getStatus()
        );
    }

    /**
     * 각 플랫폼 인원별 출석현황 조회
     */
    @Transactional(readOnly = true)
    public PlatformAttendanceResponse getPlatformAttendance(
        Platform platform,
        Long scheduleId
    ) {
        final Schedule schedule = scheduleService.getByIdOrThrow(scheduleId);
        final List<Member> members = memberService.getAllByPlatform(platform);

        final List<PlatformAttendanceResponse.MemberInfo> memberInfos =
            members.stream()
                .map(member -> PlatformAttendanceResponse.MemberInfo.of(
                    member.getName(),
                    getAttendanceInfoByMember(member, schedule.getEventList())
                ))
                .collect(Collectors.toList());

        return PlatformAttendanceResponse.of(platform, memberInfos);
    }

    private List<PlatformAttendanceResponse.AttendanceInfo> getAttendanceInfoByMember(
        Member member,
        List<Event> events
    ) {
        return events.stream().map(event -> {
            AttendanceStatus status;
            LocalDateTime attendanceAt = null;
            try {
                final Attendance attendance =
                    attendanceService.getOrThrow(member, event);
                status = attendance.getStatus();
                attendanceAt = attendance.getCreatedAt();
            } catch (NotFoundException e) {
                status = AttendanceStatus.ABSENT;
            }

            return PlatformAttendanceResponse.AttendanceInfo.of(
                status,
                attendanceAt
            );
        }).collect(Collectors.toList());
    }

}
