package kr.mashup.branding.service.scorehistory;

import java.util.List;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;
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
@Transactional
@RequiredArgsConstructor
public class ScoreHistoryService {

    private final ScoreHistoryRepository scoreHistoryRepository;

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

    public ScoreHistory convertAttendanceToScoreHistory(
        List<Attendance> attendances,
        Member member,
        Schedule schedule
    ) {
        ScoreType scoreType = ScoreType.ATTENDANCE;
        if (attendances.size() == 0) {
            scoreType = ScoreType.ABSENT;
        }
        for (Attendance attendance : attendances) {
            if (attendance.getStatus() == AttendanceStatus.LATE) {
                scoreType = ScoreType.LATE;
                break;
            }
        }

        return ScoreHistory.of(scoreType, schedule, member);
    }
}
