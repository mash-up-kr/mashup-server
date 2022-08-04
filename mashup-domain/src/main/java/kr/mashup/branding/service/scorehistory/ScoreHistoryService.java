package kr.mashup.branding.service.scorehistory;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.AttendanceStatus;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.repository.scorehistory.ScoreHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScoreHistoryService {

    private final ScoreHistoryRepository scoreHistoryRepository;

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

    public ScoreHistory calculateAttendanceToScoreHistory(List<Attendance> attendanceList, Member member,
        Schedule schedule) {
        String type = "ATTENDANCE";
        ScoreType scoreType = ScoreType.ATTENDANCE;

        if (attendanceList.size() == 0) {
            type = "ABSENT";
            scoreType = ScoreType.ABSENT;
        }
        for (Attendance attendance : attendanceList) {
            if (attendance.getStatus() == AttendanceStatus.LATE) {
                type = "LATE";
                scoreType = ScoreType.LATE;
                break;
            }
        }

        return ScoreHistory.of(type, scoreType.getName(), scoreType.getScore(), LocalDateTime.now(), schedule.getName(),
            member.getGeneration(),
            member);
    }
}
