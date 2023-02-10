package kr.mashup.branding.facade;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScoreHistoryFacadeService {

    private final ScheduleService scheduleService;
    private final AttendanceService attendanceService;
    private final ScoreHistoryService scoreHistoryService;
    private final MemberService memberService;

    @Transactional
    public List<Member> create() {
        List<Schedule> schedules = scheduleService.findAllByIsCounted(false);
        List<Member> updatedMember = new ArrayList<>();

        schedules.forEach(schedule -> {
            List<Member> members = memberService.getActiveAllByGeneration(schedule.getGeneration());

            List<ScoreHistory> scoreHistories = new ArrayList<>();
            scoreHistories.addAll(getCheckedAttendance(schedule, members));
            scoreHistories.addAll(getUnCheckedAttendance(schedule, members));
            scoreHistoryService.saveAll(scoreHistories);

            schedule.changeIsCounted(true);

            updatedMember.removeAll(members);
            updatedMember.addAll(members);
        });
        return updatedMember;
    }

    private List<ScoreHistory> getCheckedAttendance(Schedule schedule, List<Member> members) {
        Map<Member, List<Attendance>> checkedAttendances = attendanceService.getByScheduleAndGroupByMember(schedule);

        List<ScoreHistory> scoreHistories = new ArrayList<>();
        checkedAttendances.forEach((member, attendances) -> {
            scoreHistories.add(scoreHistoryService.createByAttendances(member, schedule, attendances));
            members.remove(member);
        });
        return scoreHistories;
    }

    private List<ScoreHistory> getUnCheckedAttendance(Schedule schedule, List<Member> members) {
        List<ScoreHistory> scoreHistories = new ArrayList<>();
        members.forEach((member) ->
            scoreHistories.add(ScoreHistory.of(ScoreType.ABSENT, schedule, member))
        );
        return scoreHistories;
    }


    public void delete(LocalDate scheduleStartDate) {
        Schedule schedule = scheduleService.findByStartDate(scheduleStartDate);
        List<ScoreHistory> scoreHistories = scoreHistoryService.findAttendanceScoreByDate(scheduleStartDate);

        scoreHistoryService.deleteAll(scoreHistories);
        schedule.changeIsCounted(false);
    }
}
