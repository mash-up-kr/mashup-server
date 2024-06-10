package kr.mashup.branding.facade.scorehistory;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.vo.SeminarAttendanceAppliedVo;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleType;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreHistoryProcessor {

    private final ScheduleService scheduleService;
    private final AttendanceService attendanceService;
    private final ScoreHistoryService scoreHistoryService;
    private final MemberService memberService;

    private final PushNotiEventPublisher pushNotiEventPublisher;

    /**
     * 출석점수 집계를 실행합니다.
     */
    @Scheduled(cron = "0 0 17 ? * SUN") // 매주 일요일 오후 5시
    @Transactional
    public void create() {
        List<Schedule> schedules = scheduleService.findEndedScheduleByIsCountedAndScheduleType(false, ScheduleType.ALL);
        Set<Member> updatedMember = new HashSet<>();

        schedules.forEach(schedule -> {
            List<Member> members = memberService.getActiveAllByGeneration(schedule.getGeneration());
            updatedMember.addAll(members);

            List<ScoreHistory> scoreHistories = new ArrayList<>();
            scoreHistories.addAll(getCheckedAttendance(schedule, members));
            scoreHistories.addAll(getUnCheckedAttendance(schedule, members));
            scoreHistoryService.saveAll(scoreHistories);

            schedule.changeIsCounted(true);
        });
        pushNotiEventPublisher.publishPushNotiSendEvent(new SeminarAttendanceAppliedVo(List.copyOf(updatedMember)));
    }

    /**
     * attendance 가 있는 member 의 출석점수를 계산합니다.
     *
     * @param schedule 출석점수 집계할 스케줄
     * @param members  출석체크한 멤버
     * @return 출석점수 리스트
     */
    private List<ScoreHistory> getCheckedAttendance(Schedule schedule, List<Member> members) {
        Map<Member, List<Attendance>> checkedAttendances = attendanceService.getByScheduleAndGroupByMember(schedule);

        List<ScoreHistory> scoreHistories = new ArrayList<>();
        checkedAttendances.forEach((member, attendances) -> {
            scoreHistories.add(scoreHistoryService.createByAttendances(member, schedule, attendances));
            members.remove(member);
        });
        return scoreHistories;
    }

    /**
     * attendance 가 없는 member 의 출석점수(결석처리)를 계산합니다.
     *
     * @param schedule 출석점수 집계할 스케줄
     * @param members  출석체크를 한번도 하지않은 멤버
     * @return 출석점수 리스트
     */
    private List<ScoreHistory> getUnCheckedAttendance(Schedule schedule, List<Member> members) {
        List<ScoreHistory> scoreHistories = new ArrayList<>();
        members.forEach((member) ->
            scoreHistories.add(ScoreHistory.of(ScoreType.ABSENT, schedule, member))
        );
        return scoreHistories;
    }

    /**
     * schedule 의 startDate 기준으로 출석점수 집계 결과를 삭제합니다.
     *
     * @param scheduleStartDate 스케줄 시작 일자
     */
    public void delete(LocalDate scheduleStartDate) {
        Schedule schedule = scheduleService.findScheduleByStartDateAndScheduleType(scheduleStartDate, ScheduleType.ALL);
        List<ScoreHistory> scoreHistories = scoreHistoryService.findAttendanceScoreByDate(scheduleStartDate);

        scoreHistoryService.deleteAll(scoreHistories);
        schedule.changeIsCounted(false);
    }
}
