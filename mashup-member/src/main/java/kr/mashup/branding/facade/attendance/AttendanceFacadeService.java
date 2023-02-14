package kr.mashup.branding.facade.attendance;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.exception.GenerationIntegrityFailException;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceEndingVo;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceStartedVo;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceStartingVo;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.attendance.AttendanceCodeService;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.attendance.response.*;
import kr.mashup.branding.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceFacadeService {

    private final static int LATE_LIMIT_TIME = 20;
    private final static long PUSH_SCHEDULE_INTERVAL_MINUTES = 1;
    private final static long ATTENDANCE_START_AFTER_MINUTES = 1;
    private final static long ATTENDANCE_END_AFTER_MINUTES = 3;
    private final AttendanceService attendanceService;
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final AttendanceCodeService attendanceCodeService;
    private final PushNotiEventPublisher pushNotiEventPublisher;

    /**
     * 출석 체크
     */
    @Transactional
    public AttendanceCheckResponse checkAttendance(
            final Long memberId,
            final String checkingCode
    ) {

        final LocalDateTime checkTime = LocalDateTime.now();
        final Member member = memberService.getActiveOrThrowById(memberId);

        final Event event;
        final AttendanceCode attendanceCode;
        try {
            attendanceCode = attendanceCodeService.getByCodeOrThrow(checkingCode);
            event = attendanceCode.getEvent();
        } catch (NotFoundException e) {
            throw new BadRequestException(ResultCode.ATTENDANCE_CODE_INVALID);
        }


        //멤버 최신 기수 확인
        final int latestGenerationNumber = member.getMemberGenerations()
                .stream()
                .map(MemberGeneration::getGeneration)
                .max(Comparator.comparingInt(Generation::getNumber))
                .orElseThrow(GenerationIntegrityFailException::new)
                .getNumber();

        final int eventGenerationNumber = event.getSchedule().getGeneration().getNumber();

        if (eventGenerationNumber != latestGenerationNumber) {
            throw new NotFoundException(ResultCode.EVENT_NOT_FOUND);
        }


        validEventTime(event.getStartedAt(), event.getEndedAt(), checkTime);
        validAttendanceCode(attendanceCode, checkingCode);
        validAlreadyCheckAttendance(member, event);

        // 출석 체크
        Attendance attendance = attendanceService.checkAttendance(
                member,
                event,
                getAttendanceStatus(attendanceCode, checkTime)
        );

        return AttendanceCheckResponse.from(attendance);
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional(readOnly = true)
    public void sendAttendanceStartingPushNoti() {
        findAllStartsWithin(ATTENDANCE_START_AFTER_MINUTES)
                .forEach(attendanceCode -> pushNotiEventPublisher.publishPushNotiSendEvent(
                        new AttendanceStartingVo(memberService.getAllPushNotiTargetableMembers())
                ));
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional(readOnly = true)
    public void sendAttendanceStartedPushNoti() {
        findAllStartsWithin(0L)
                .forEach(attendanceCode -> pushNotiEventPublisher.publishPushNotiSendEvent(
                        new AttendanceStartedVo(memberService.getAllPushNotiTargetableMembers())
                ));
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional(readOnly = true)
    public void sendAttendanceEndingPushNoti() {
        findAllEndsWithin(ATTENDANCE_END_AFTER_MINUTES)
                .forEach(attendanceCode -> pushNotiEventPublisher.publishPushNotiSendEvent(
                        new AttendanceEndingVo(memberService.getAllPushNotiTargetableMembers())
                ));
    }

    private List<AttendanceCode> findAllStartsWithin(Long afterMinutes) {
        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return attendanceCodeService.findByStartedAtLeftOpenBetween(
                now.plusMinutes(-PUSH_SCHEDULE_INTERVAL_MINUTES + afterMinutes),
                now.plusMinutes(afterMinutes)
        );
    }

    private List<AttendanceCode> findAllEndsWithin(Long afterMinutes) {
        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return attendanceCodeService.findAllByEndedAtLeftOpenBetween(
                now.plusMinutes(-PUSH_SCHEDULE_INTERVAL_MINUTES + afterMinutes),
                now.plusMinutes(afterMinutes)
        );
    }

    private void validAttendanceCode(AttendanceCode attendanceCode, String code) {
        if (attendanceCode == null) {
            throw new BadRequestException(ResultCode.ATTENDANCE_CODE_NOT_FOUND);
        }

        if (!attendanceCode.getCode().equals(code)) {
            throw new BadRequestException(ResultCode.ATTENDANCE_CODE_INVALID);
        }
    }

    private void validEventTime(
            LocalDateTime startedAt,
            LocalDateTime endedAt,
            LocalDateTime time
    ) {
        if (time.isBefore(startedAt)) {
            throw new BadRequestException(ResultCode.ATTENDANCE_TIME_BEFORE);
        }

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
        if (isAttendance) return AttendanceStatus.ATTENDANCE;

        final boolean isLate = DateUtil.isInTime(
                attendanceCode.getEndedAt(),
                attendanceCode.getEndedAt().plusMinutes(LATE_LIMIT_TIME),
                checkTime
        );
        if (isLate) return AttendanceStatus.LATE;

        throw new BadRequestException(ResultCode.ATTENDANCE_TIME_OVER);
    }

    /**
     * 플랫폼별 전체 출석현황 조회
     */
    @Transactional(readOnly = true)
    public TotalAttendanceResponse getTotalAttendance(Long scheduleId) {

        final LocalDateTime now = LocalDateTime.now();
        final Schedule schedule = scheduleService.getByIdAndStatusOrThrow(scheduleId, ScheduleStatus.PUBLIC);
        final Generation currentGeneration = schedule.getGeneration();

        final List<Event> startedEvents =
                filterStartedEvent(schedule.getEventList(), now);

        final List<TotalAttendanceResponse.PlatformInfo> platformInfos =
                Arrays.stream(Platform.values()).map(platform -> {
                    // 플랫폼과 기수를 통해 멤버들을 가져옴
                    final List<Member> members = memberService.getAllByPlatformAndGeneration(
                            platform,
                            currentGeneration
                    );

                    // 해당 플랫폼의 출석 정보를 가져옴
                    final Map<String, Long> attendanceStatusCount =
                            getAttendanceStatusCountOfPlatform(members, startedEvents);

                    final Long attendanceCount =
                            attendanceStatusCount.getOrDefault(
                                    AttendanceStatus.ATTENDANCE.name(),
                                    0L
                            );
                    final Long lateCount =
                            attendanceStatusCount.getOrDefault(
                                    AttendanceStatus.LATE.name(),
                                    0L
                            );

                    return TotalAttendanceResponse.PlatformInfo.of(
                            platform,
                            (long) members.size(),
                            attendanceCount,
                            lateCount
                    );
                }).collect(Collectors.toList());

        return TotalAttendanceResponse.of(
                platformInfos,
                startedEvents.size(),
                isEndStartedEvent(startedEvents, now)
        );
    }

    private List<Event> filterStartedEvent(
            List<Event> events,
            LocalDateTime now
    ) {
        return events.stream()
                .filter(event -> !now.isBefore(event.getStartedAt()))
                .collect(Collectors.toList());
    }

    private Map<String, Long> getAttendanceStatusCountOfPlatform(
            List<Member> members,
            List<Event> startedEvents
    ) {
        return members.stream()
                .map(member ->
                        attendanceService.getAttendanceStatusByMemberAndStartedEvents(
                                member,
                                startedEvents))
                .collect(Collectors.groupingBy(
                        AttendanceStatus::name,
                        Collectors.counting()
                ));
    }

    private boolean isEndStartedEvent(
            List<Event> startedEvents,
            LocalDateTime now
    ) {
        if (startedEvents.isEmpty()) return false;

        final Event lastEvent = startedEvents.get(startedEvents.size() - 1);

        final LocalDateTime attendanceEndTime =
                lastEvent
                        .getAttendanceCodes()
                        .get(lastEvent.getAttendanceCodes().size() - 1)
                        .getEndedAt().plusMinutes(10);

        return now.isAfter(attendanceEndTime);
    }


    /**
     * 각 플랫폼 인원별 출석현황 조회
     */
    @Transactional(readOnly = true)
    public PlatformAttendanceResponse getPlatformAttendance(
            Platform platform,
            Long scheduleId
    ) {
        final Schedule schedule = scheduleService.getByIdAndStatusOrThrow(scheduleId, ScheduleStatus.PUBLIC);
        final Generation currentGeneration = schedule.getGeneration();

        final List<Member> members =
                memberService.getAllByPlatformAndGeneration(
                        platform,
                        currentGeneration
                );

        final List<PlatformAttendanceResponse.MemberInfo> memberInfos =
                members.stream()
                        .map(member -> PlatformAttendanceResponse.MemberInfo.of(
                                member.getName(),
                                getAttendanceInfoByMember(
                                        member,
                                        schedule.getEventList(),
                                        schedule.getStartedAt()
                                )
                        ))
                        .collect(Collectors.toList());

        return PlatformAttendanceResponse.of(platform, memberInfos);
    }

    /**
     * 개인별 출석현황 조회
     */
    @Transactional(readOnly = true)
    public PersonalAttendanceResponse getPersonalAttendance(
            Long memberId,
            Long scheduleId
    ) {
        final Member member = memberService.getActiveOrThrowById(memberId);
        final Schedule schedule = scheduleService.getByIdAndStatusOrThrow(scheduleId, ScheduleStatus.PUBLIC);

        final List<AttendanceInfo> attendanceInfos =
                getAttendanceInfoByMember(
                        member,
                        schedule.getEventList(),
                        schedule.getStartedAt()
                );

        return PersonalAttendanceResponse.of(member.getName(), attendanceInfos);
    }

    private List<AttendanceInfo> getAttendanceInfoByMember(
            Member member,
            List<Event> events,
            LocalDateTime scheduleStartedAt
    ) {
        final LocalDateTime now = LocalDateTime.now();
        // 스케줄 시작 전에는 빈 리스트를 내려준다.
        if (now.isBefore(scheduleStartedAt)) {
            return Collections.emptyList();
        }

        return events.stream().map(event -> {
            AttendanceStatus status;
            LocalDateTime attendanceAt = null;
            try {
                final Attendance attendance =
                        attendanceService.getOrThrow(member, event);
                status = attendance.getStatus();
                attendanceAt = attendance.getCreatedAt();
            } catch (NotFoundException e) {
                final List<AttendanceCode> attendanceCodes = event.getAttendanceCodes();
                if (attendanceCodes.isEmpty()) {
                    status = AttendanceStatus.NOT_YET;
                } else {
                    final AttendanceCode code = attendanceCodes.get(event.getAttendanceCodes().size() - 1);
                    final boolean isBeforeAttendanceCheckTime =
                            now.isBefore(code.getStartedAt());
                    final boolean isAttendanceCheckTime = DateUtil.isInTime(
                            code.getStartedAt(),
                            code.getEndedAt().plusMinutes(LATE_LIMIT_TIME),
                            now
                    );
                    // 출석체크 시작 전이거나(2부에서는 해당 조건을 체크),
                    // 출석 체크 시간인데 출석을 안했을 때는 결석이 아닌 아직이란 값을 내려줌
                    if (isBeforeAttendanceCheckTime || isAttendanceCheckTime) {
                        status = AttendanceStatus.NOT_YET;
                    } else {
                        status = AttendanceStatus.ABSENT;
                    }
                }
            }

            return AttendanceInfo.of(
                    status,
                    attendanceAt
            );
        }).collect(Collectors.toList());
    }
}
