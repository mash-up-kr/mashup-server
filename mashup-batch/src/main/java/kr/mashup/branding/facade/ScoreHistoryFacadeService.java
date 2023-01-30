package kr.mashup.branding.facade;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<Member> create() {
        List<Schedule> schedules = scheduleService.findAllByIsCounted(false);
        List<Member> members = new ArrayList<>();

        schedules.forEach(schedule -> {
            Map<Member, List<Attendance>> attendanceMap = attendanceService.getByScheduleAndGroupByMember(schedule);
            attendanceMap.forEach((member, attendances) -> {
                ScoreHistory scoreHistory = scoreHistoryService.createByAttendances(member, schedule, attendances);
                scoreHistoryService.save(scoreHistory);
                members.add(member);
            });
            schedule.changeIsCounted(true);
        });
        return members;
    }

    public void delete(LocalDate scheduleStartDate) {
        Schedule schedule = scheduleService.findByStartedAt(scheduleStartDate);
        List<ScoreHistory> scoreHistories = scoreHistoryService.findByScheduleStartedAt(scheduleStartDate);

        scoreHistoryService.deleteAll(scoreHistories);
        schedule.changeIsCounted(false);
    }
}
