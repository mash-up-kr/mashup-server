package kr.mashup.branding.service.scorehistory;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.repository.event.EventRepository;
import kr.mashup.branding.repository.scorehistory.ScoreHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScoreHistoryService {

    private final ScoreHistoryRepository scoreHistoryRepository;
    private final EventRepository eventRepository;

    @Transactional
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

    @Transactional
    public void deleteById(Long id) {
        scoreHistoryRepository.deleteById(id);
    }

    public ScoreHistory convertAttendanceToScoreHistory(
        List<Attendance> attendances,
        Member member,
        Schedule schedule
    ) {
        ScoreType scoreType = ScoreType.ABSENT;

        List<Event> eventList = eventRepository.findBySchedule(schedule);
        int number = 0;
        if (attendances.size() == 0) {
            scoreType = ScoreType.ABSENT;
        }
        for (Attendance attendance : attendances) {
            if (attendance.getStatus() == AttendanceStatus.LATE) {
                scoreType = ScoreType.LATE;
                break;
            }
            number++;
        }

        if (number == eventList.size()) {
            scoreType = ScoreType.ATTENDANCE;
        }

        return ScoreHistory.of(
            scoreType.toString(),
            scoreType.getName(),
            scoreType.getScore(),
            schedule.getStartedAt(),
            schedule.getName(),
            schedule.getGeneration(),
            member
        );
    }
}
