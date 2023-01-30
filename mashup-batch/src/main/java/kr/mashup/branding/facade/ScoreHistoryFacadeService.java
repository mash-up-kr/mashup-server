package kr.mashup.branding.facade;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.repository.schedule.ScheduleRepository;
import kr.mashup.branding.repository.scorehistory.ScoreHistoryRepository;
import kr.mashup.branding.service.attendance.AttendanceService;
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

    private final ScheduleRepository scheduleRepository;
    private final AttendanceService attendanceService;
    private final ScoreHistoryService scoreHistoryService;
    private final ScoreHistoryRepository scoreHistoryRepository;

    public List<Member> calculate() {
        List<Schedule> schedules = scheduleRepository.findAllByIsCounted(false);
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

    public void delete(String scheduleStartDate) {
        LocalDate startDate = LocalDate.parse(scheduleStartDate);

        Schedule schedule = scheduleRepository.retrieveByStartedAt(startDate);
        List<ScoreHistory> scoreHistories = scoreHistoryRepository.retrieveByScheduleStartedAt(startDate);
        scoreHistoryRepository.deleteAll(scoreHistories);
        schedule.changeIsCounted(false);
    }
}
