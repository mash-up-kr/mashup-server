package kr.mashup.branding.repository.scorehistory;

import kr.mashup.branding.domain.scorehistory.ScoreHistory;

import java.time.LocalDate;
import java.util.List;

public interface ScoreHistoryRepositoryCustom {

    List<ScoreHistory> retrieveAttendanceScoreByDate(LocalDate date);
}
