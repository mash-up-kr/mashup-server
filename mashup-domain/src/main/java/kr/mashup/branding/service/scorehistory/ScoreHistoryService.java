package kr.mashup.branding.service.scorehistory;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.repository.event.EventRepository;
import kr.mashup.branding.repository.scorehistory.ScoreHistoryRepository;
import lombok.RequiredArgsConstructor;

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
}
