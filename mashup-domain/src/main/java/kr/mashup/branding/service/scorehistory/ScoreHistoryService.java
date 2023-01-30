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
     * TODO: 리팩토링 진행 by @hocaron
     */
    private ScoreType getScoreTypeByAttendances(
        List<Event> events,
        List<Attendance> attendances
    ) {
        ScoreType scoreType = null;

        final long attendanceNumber = attendances.stream()
            .filter(attendance -> attendance.getStatus() == AttendanceStatus.ATTENDANCE)
            .count();
        final long lateNumber = attendances.size() - attendanceNumber;
        final long absentNumber = events.size() - attendances.size();

        if(attendanceNumber == events.size()){      // 출석한 개수와 이벤트 개수가 같은 경우
            scoreType = ScoreType.ATTENDANCE;
        } else if (absentNumber > 0) {              // 결석이 하나 이상인 경우
            scoreType = ScoreType.ABSENT;
        } else if (lateNumber > 0) {                // 지각이 하나 이상인 경우
            scoreType = ScoreType.LATE;
        }
        return scoreType;
    }

    public void deleteAll(List<ScoreHistory> scoreHistories) {
        scoreHistoryRepository.deleteAll(scoreHistories);
    }

    public List<ScoreHistory> findByScheduleStartedAt(LocalDate startDate) {
        return scoreHistoryRepository.retrieveByScheduleStartedAt(startDate);
    }
}
