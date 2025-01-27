package kr.mashup.branding.service.scorehistory;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.repository.event.EventRepository;
import kr.mashup.branding.repository.scorehistory.ScoreHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScoreHistoryService {

    private final ScoreHistoryRepository scoreHistoryRepository;
    private final EventRepository eventRepository;

    public ScoreHistory save(ScoreHistory scoreHistory) {
        scoreHistoryRepository.save(scoreHistory);
        return scoreHistory;
    }

    public List<ScoreHistory> getByMember(Member member) {
        return scoreHistoryRepository.findByMember(member);
    }

    public List<ScoreHistory> getByMemberAndGeneration(Member member, Generation generation) {
        return scoreHistoryRepository.findByMemberAndGenerationOrderByDateAsc(member, generation);
    }

    public ScoreHistory getByIdOrThrow(Long id){
        return scoreHistoryRepository.findById(id).orElseThrow(()-> new NotFoundException(ResultCode.SCOREHISTORY_NOT_FOUND));
    }

    public void deleteById(Long id) {
        scoreHistoryRepository.deleteById(id);
    }

    public ScoreHistory createByAttendances(
        Member member,
        Schedule schedule,
        List<Attendance> attendances
    ) {
        List<Event> events = eventRepository.findBySchedule(schedule);
        ScoreType scoreType = getScoreTypeByAttendances(events, attendances);

        return ScoreHistory.of(scoreType, schedule, member);
    }

    /**
     * 출석 결과로 최종 출석 결과를 생성한다.
     */
    private ScoreType getScoreTypeByAttendances(
        List<Event> events,
        List<Attendance> attendances
    ) {
        long attendanceCount = countAttendance(attendances);
        long lateCount = countLate(attendances, attendanceCount);
        long absentCount = countAbsent(events, attendances);

        if (absentCount > 0) {
            return ScoreType.ABSENT; // 결석이 하나 이상인 경우
        }
        if (lateCount > 0) {
            return ScoreType.LATE;   // 지각이 하나 이상인 경우
        }
        return ScoreType.ATTENDANCE; // 모두 출석한 경우
    }

    private long countAttendance(List<Attendance> attendances) {
        return attendances.stream()
            .filter(attendance -> attendance.getStatus() == AttendanceStatus.ATTENDANCE)
            .count();
    }

    private long countLate(List<Attendance> attendances, long attendanceNumber) {
        return attendances.size() - attendanceNumber;
    }

    private long countAbsent(List<Event> events, List<Attendance> attendances) {
        return events.size() - attendances.size();
    }

    public void deleteAll(List<ScoreHistory> scoreHistories) {
        scoreHistoryRepository.deleteAll(scoreHistories);
    }

    public List<ScoreHistory> findAttendanceScoreByDate(LocalDate date) {
        return scoreHistoryRepository.retrieveAttendanceScoreByDate(date);
    }

    public List<ScoreHistory> saveAll(List<ScoreHistory> scoreHistories) {
        return scoreHistoryRepository.saveAll(scoreHistories);
    }
}
