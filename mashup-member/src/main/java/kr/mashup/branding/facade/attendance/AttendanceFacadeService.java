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
import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.entity.Position;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceDiscordVo;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceAbsentForLeaderVo;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceEndingVo;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceLateForLeaderVo;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceStartedVo;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceStartingVo;
import kr.mashup.branding.domain.pushnoti.vo.PushNotiSendVo;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import kr.mashup.branding.service.attendance.AttendanceCodeService;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.attendance.request.AttendanceCheckRequest;
import kr.mashup.branding.ui.attendance.response.*;
import kr.mashup.branding.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceFacadeService {

    private final static long PUSH_SCHEDULE_INTERVAL_MINUTES = 1;
    private final static long ATTENDANCE_START_AFTER_MINUTES = 1;
    private final static long ATTENDANCE_END_AFTER_MINUTES = 3;
    private final static double ATTENDANCE_DISTANCE = 1000;

    @Value("${attendance.leader-noti.before-minutes:3}")
    private long leaderNotiBeforeMinutes;

    private final AttendanceService attendanceService;
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final AttendanceCodeService attendanceCodeService;
    private final PushNotiEventPublisher pushNotiEventPublisher;
    private final AdminMemberService adminMemberService;

    /**
     * 출석 체크
     */
    @Transactional
    public AttendanceCheckResponse checkAttendance(
            final Long memberId,
            final AttendanceCheckRequest request
    ) {

        final LocalDateTime checkTime = LocalDateTime.now();
        final Member member = memberService.getActiveOrThrowById(memberId);

        final Event event;
        final AttendanceCode attendanceCode;
        try {
            attendanceCode = attendanceCodeService.getByCodeOrThrow(request.getCheckingCode());
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

        validateAlreadyCheckAttendance(member, event);
        validateWithinAttendanceDistance(request.getLatitude(), request.getLongitude(), event.getSchedule());

        // 출석 체크
        final Attendance attendance = attendanceService.checkAttendance(
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
                .forEach(attendanceCode -> {

                    final Event checkingEvent = attendanceCode.getEvent();
                    final Schedule schedule = checkingEvent.getSchedule();

                    final List<Member> pushableMembers = memberService.getPushNotiTargetableMembersBySchedule(schedule);

                    final List<Member> pushTargetMembers = removeAlreadyCheckedMembers(checkingEvent, pushableMembers);

                    pushNotiEventPublisher.publishPushNotiSendEvent(new AttendanceStartingVo(pushTargetMembers));
                });
    }

    private List<Member> removeAlreadyCheckedMembers(
            final Event checkingEvent,
            final List<Member> pushableMembers) {

        final List<Member> alreadyCheckedMembers
                = attendanceService
                .getByEvent(checkingEvent)
                .stream()
                .map(Attendance::getMember)
                .collect(Collectors.toList());

        final List<Member> pushTargetMembers = new ArrayList<>();
        pushTargetMembers.addAll(pushableMembers);
        pushTargetMembers.removeAll(alreadyCheckedMembers);

        return pushTargetMembers;
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional(readOnly = true)
    public void sendAttendanceStartedPushNoti() {
        findAllStartsWithin(0L)
                .forEach(attendanceCode -> {
                    final Event checkingEvent = attendanceCode.getEvent();
                    final Schedule schedule = checkingEvent.getSchedule();

                    final List<Member> pushableMembers = memberService.getPushNotiTargetableMembersBySchedule(schedule);

                    final List<Member> pushTargetMembers = removeAlreadyCheckedMembers(checkingEvent, pushableMembers);
                    pushNotiEventPublisher.publishPushNotiSendEvent(new AttendanceStartedVo(pushTargetMembers));
                });
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional(readOnly = true)
    public void sendAttendanceEndingPushNoti() {
        findAllEndsWithin(ATTENDANCE_END_AFTER_MINUTES)
                .forEach(attendanceCode -> {
                    final Event checkingEvent = attendanceCode.getEvent();
                    final Schedule schedule = checkingEvent.getSchedule();

                    final List<Member> pushableMembers = memberService.getPushNotiTargetableMembersBySchedule(schedule);

                    final List<Member> pushTargetMembers = removeAlreadyCheckedMembers(checkingEvent, pushableMembers);

                    pushNotiEventPublisher.publishPushNotiSendEvent(new AttendanceEndingVo(pushTargetMembers));
                });
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

    /**
     * 출석 마감 N분 전: 플랫폼 리더에게 미출석자 명단 푸시
     */
    @Scheduled(cron = "0 * * * * *")
    @Transactional(readOnly = true)
    public void sendAttendanceLatePushNotiToLeaders() {
        sendLeaderPushNoti(findAllEndsWithin(leaderNotiBeforeMinutes), AttendanceLateForLeaderVo::new, "⏰ 출석 마감");
    }

    /**
     * 지각 마감 N분 전: 플랫폼 리더에게 미출석자 명단 푸시
     */
    @Scheduled(cron = "0 * * * * *")
    @Transactional(readOnly = true)
    public void sendAttendanceAbsentPushNotiToLeaders() {
        sendLeaderPushNoti(findAllLatenessEndsWithin(leaderNotiBeforeMinutes), AttendanceAbsentForLeaderVo::new, "⚠️ 지각 마감");
    }

    /**
     * 지각 마감 시점: Discord에 최종 출석 결과 발송
     */
    @Scheduled(cron = "0 * * * * *")
    @Transactional(readOnly = true)
    public void sendAttendanceFinalResultToDiscord() {
        final List<AttendanceCode> attendanceCodes = findAllLatenessEndsWithin(0L);
        if (attendanceCodes.isEmpty()) return;

        for (AttendanceCode attendanceCode : attendanceCodes) {
            final Event event = attendanceCode.getEvent();
            final Schedule schedule = event.getSchedule();
            final Generation generation = schedule.getGeneration();

            final List<Attendance> attendances = attendanceService.getByEvent(event);
            int totalAttendance = (int) attendances.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.ATTENDANCE).count();
            int totalLate = (int) attendances.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.LATE).count();

            final Map<Member, AttendanceStatus> memberStatusMap = new HashMap<>();
            attendances.forEach(a -> memberStatusMap.put(a.getMember(), a.getStatus()));

            int totalMembers = 0;
            final Map<Platform, List<Member>> absentByPlatform = new LinkedHashMap<>();
            final Map<Platform, List<Member>> lateByPlatform = new LinkedHashMap<>();

            for (Platform platform : Platform.values()) {
                if (!schedule.checkAvailabilityByPlatform(platform)) continue;
                final List<Member> platformMembers =
                        memberService.getAllByPlatformAndGeneration(platform, generation);
                totalMembers += platformMembers.size();

                final List<Member> absent = new ArrayList<>();
                final List<Member> late = new ArrayList<>();
                for (Member m : platformMembers) {
                    final AttendanceStatus status = memberStatusMap.get(m);
                    if (status == null) {
                        absent.add(m);
                    } else if (status == AttendanceStatus.LATE) {
                        late.add(m);
                    }
                }
                absentByPlatform.put(platform, absent);
                lateByPlatform.put(platform, late);
            }

            final int totalAbsent = totalMembers - totalAttendance - totalLate;
            final String content = buildDiscordContent(
                    absentByPlatform, lateByPlatform, "📊 최종 결과", schedule.getName(), null,
                    totalAttendance, totalLate, totalAbsent, totalMembers);
            if (content != null) {
                pushNotiEventPublisher.publishDiscordEvent(new AttendanceDiscordVo(content));
            }
        }
    }

    private void sendLeaderPushNoti(
            final List<AttendanceCode> attendanceCodes,
            final LeaderNotiVoFactory voFactory,
            final String notiLabel
    ) {
        if (attendanceCodes.isEmpty()) return;

        final Map<Platform, List<Member>> leadersByPlatform = resolveLeadersByPlatform();
        final Map<Platform, List<Member>> notCheckedByPlatform = new LinkedHashMap<>();
        int totalAttendance = 0;
        int totalLate = 0;
        int totalMembers = 0;
        String scheduleName = "";
        LocalDateTime deadlineAt = null;

        for (AttendanceCode attendanceCode : attendanceCodes) {
            final Event event = attendanceCode.getEvent();
            final Schedule schedule = event.getSchedule();
            final Generation generation = schedule.getGeneration();
            scheduleName = schedule.getName();
            deadlineAt = attendanceCode.getAttendanceCheckEndedAt();

            final List<Attendance> attendances = attendanceService.getByEvent(event);
            final List<Member> checkedMembers = attendances.stream()
                    .map(Attendance::getMember)
                    .collect(Collectors.toList());

            totalAttendance += attendances.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.ATTENDANCE)
                    .count();
            totalLate += attendances.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.LATE)
                    .count();

            for (Platform platform : Platform.values()) {
                if (!schedule.checkAvailabilityByPlatform(platform)) continue;

                final List<Member> platformMembers =
                        memberService.getAllByPlatformAndGeneration(platform, generation);
                totalMembers += platformMembers.size();

                final List<Member> notCheckedMembers = new ArrayList<>(platformMembers);
                notCheckedMembers.removeAll(checkedMembers);

                notCheckedByPlatform.put(platform, notCheckedMembers);

                if (notCheckedMembers.isEmpty()) continue;

                final List<Member> leaderMembers = leadersByPlatform.getOrDefault(platform, Collections.emptyList());
                if (leaderMembers.isEmpty()) continue;

                final String names = formatNames(notCheckedMembers);

                pushNotiEventPublisher.publishPushNotiSendEvent(
                        voFactory.create(leaderMembers, platform.getName(), names));
            }
        }

        final int totalAbsent = totalMembers - totalAttendance - totalLate;
        final String discordContent = buildDiscordContent(
                notCheckedByPlatform, null, notiLabel, scheduleName, deadlineAt,
                totalAttendance, totalLate, totalAbsent, totalMembers);
        if (discordContent != null) {
            pushNotiEventPublisher.publishDiscordEvent(new AttendanceDiscordVo(discordContent));
        }
    }

    @FunctionalInterface
    private interface LeaderNotiVoFactory {
        PushNotiSendVo create(List<Member> leaders, String platformName, String names);
    }

    private Map<Platform, List<Member>> resolveLeadersByPlatform() {
        final List<AdminMember> leaders = adminMemberService.getLeadersWithMemberId();
        if (leaders.isEmpty()) return Collections.emptyMap();

        final List<Long> memberIds = leaders.stream()
                .map(AdminMember::getMemberId)
                .collect(Collectors.toList());
        final Map<Long, Member> memberMap = memberService.findAllByIds(memberIds).stream()
                .collect(Collectors.toMap(Member::getId, Function.identity()));

        final Map<Platform, List<Member>> result = new HashMap<>();
        for (AdminMember admin : leaders) {
            final Member member = memberMap.get(admin.getMemberId());
            if (member == null) {
                log.warn("[LEADER_NOTI] member not found for adminMemberId={}, memberId={}",
                        admin.getAdminMemberId(), admin.getMemberId());
                continue;
            }
            for (Position.Team team : admin.getPosition().getAuthorities()) {
                final Platform platform = Position.toPlatform(team);
                result.computeIfAbsent(platform, k -> new ArrayList<>()).add(member);
            }
        }
        return result;
    }

    private String buildDiscordContent(
            final Map<Platform, List<Member>> notCheckedByPlatform,
            final Map<Platform, List<Member>> lateByPlatform,
            final String notiLabel,
            final String scheduleName,
            final LocalDateTime deadlineAt,
            final int totalAttendance,
            final int totalLate,
            final int totalAbsent,
            final int totalMembers
    ) {
        if (notCheckedByPlatform.isEmpty()) return null;

        final StringBuilder sb = new StringBuilder();
        sb.append("---\n");
        sb.append("**").append(scheduleName).append("** | ").append(notiLabel);
        if (deadlineAt != null) {
            sb.append(" ").append(leaderNotiBeforeMinutes).append("분 전")
                    .append(" (마감 ").append(String.format("%02d:%02d", deadlineAt.getHour(), deadlineAt.getMinute())).append(")");
        }
        sb.append("\n\n");

        final List<String> allClear = new ArrayList<>();
        final List<String> issues = new ArrayList<>();

        for (Platform platform : Platform.values()) {
            final List<Member> absentMembers = notCheckedByPlatform.get(platform);
            final boolean hasAbsent = absentMembers != null && !absentMembers.isEmpty();

            if (lateByPlatform != null) {
                final List<Member> lateMembers = lateByPlatform.get(platform);
                final boolean hasLate = lateMembers != null && !lateMembers.isEmpty();

                if (!hasAbsent && !hasLate) {
                    allClear.add(platform.getName());
                } else {
                    final StringBuilder line = new StringBuilder();
                    line.append(platform.getName());
                    if (hasLate) {
                        line.append(" | 지각 **").append(formatNames(lateMembers)).append("**");
                    }
                    if (hasAbsent) {
                        line.append(" | 결석 **").append(formatNames(absentMembers)).append("**");
                    }
                    issues.add(line.toString());
                }
            } else {
                if (!hasAbsent) {
                    allClear.add(platform.getName());
                } else {
                    issues.add(platform.getName() + " (**" + absentMembers.size()
                            + "명**): **" + formatNames(absentMembers) + "**");
                }
            }
        }

        for (String issue : issues) {
            sb.append(issue).append("\n");
        }
        if (!allClear.isEmpty()) {
            sb.append(String.join(", ", allClear)).append(": ✅ 전원 출석\n");
        }

        final int attendanceRate = totalMembers > 0 ? (totalAttendance * 100 / totalMembers) : 0;
        sb.append("\n> 출석 **").append(totalAttendance).append("명** (").append(attendanceRate).append("%)")
                .append(" / 지각 **").append(totalLate).append("명**")
                .append(" / 결석 **").append(totalAbsent).append("명**");

        return sb.toString();
    }

    private String formatNames(List<Member> members) {
        final List<String> nameList = members.stream()
                .map(Member::getName)
                .collect(Collectors.toList());
        if (nameList.size() > 20) {
            return String.join(", ", nameList.subList(0, 20)) + " 외 " + (nameList.size() - 20) + "명";
        }
        return String.join(", ", nameList);
    }

    private List<AttendanceCode> findAllLatenessEndsWithin(Long afterMinutes) {
        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return attendanceCodeService.findAllByLatenessEndedAtLeftOpenBetween(
                now.plusMinutes(-PUSH_SCHEDULE_INTERVAL_MINUTES + afterMinutes),
                now.plusMinutes(afterMinutes)
        );
    }

    /**
     * 이미 출석 체크를 했는지 판별
     */
    private void validateAlreadyCheckAttendance(Member member, Event event) {
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
                attendanceCode.getAttendanceCheckStartedAt(),
                attendanceCode.getAttendanceCheckEndedAt(),
                checkTime
        );
        if (isAttendance) return AttendanceStatus.ATTENDANCE;

        final boolean isLate = DateUtil.isInTime(
                attendanceCode.getAttendanceCheckEndedAt(),
                attendanceCode.getLatenessCheckEndedAt(),
                checkTime
        );
        if (isLate) return AttendanceStatus.LATE;

        throw new BadRequestException(ResultCode.ATTENDANCE_TIME_OVER);
    }

    /**
     * 플랫폼별 전체 출석현황 조회
     */
    @Transactional(readOnly = true)
    public TotalAttendanceResponse getTotalAttendance(final Long scheduleId) {

        final Schedule schedule = scheduleService.getByIdAndStatusOrThrow(scheduleId, ScheduleStatus.PUBLIC);
        final Generation currentGeneration = schedule.getGeneration();

        final LocalDateTime now = LocalDateTime.now();
        final List<Event> startedEvents = filterStartedEvent(schedule.getEventList(), now);

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
                .filter(event -> now.isAfter(event.getStartedAt()))
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
                        .getAttendanceCode()
                        .getLatenessCheckEndedAt();

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
                                member.getId(),
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
            return Collections.nCopies(events.size(), AttendanceInfo.beforeSchedule());
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
                final AttendanceCode code = event.getAttendanceCode();
                final boolean isBeforeAttendanceCheckTime =
                        now.isBefore(code.getAttendanceCheckStartedAt());
                final boolean isAttendanceCheckTime = DateUtil.isInTime(
                        code.getAttendanceCheckStartedAt(),
                        code.getLatenessCheckEndedAt(),
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

            return AttendanceInfo.of(
                    status,
                    attendanceAt
            );
        }).collect(Collectors.toList());
    }

    /**
     * 현재 위치가 출석체크 가능한지 판별
     */
    private void validateWithinAttendanceDistance(Double latitude, Double longitude, Schedule schedule) {

        if (schedule.isOnline()) {
            return;
        }

        final boolean isWithinAttendanceDistance = schedule.getLocation().isWithinDistance(latitude, longitude, ATTENDANCE_DISTANCE);
        if (!isWithinAttendanceDistance) {
            log.info("[ATTENDANCE_DISTANCE] isWithinAttendanceDistance lat:{}, lon:{}, scheduleId:{}",
                latitude, longitude,schedule.getId());
            throw new BadRequestException(ResultCode.ATTENDANCE_DISTANCE_OUT_OF_RANGE);
        }
    }
}
