package kr.mashup.branding.ui.scorehistory.response;

import java.time.LocalDate;

import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class ScoreHistoryResponse {

    Long scoreHistoryId;

    String scoreType;

    String scheduleName;

    LocalDate date;

    Double score;

    public static ScoreHistoryResponse from(ScoreHistory scoreHistory) {
        return ScoreHistoryResponse.of(
            scoreHistory.getId(),
            scoreHistory.getScoreType().getDescription(),
            scoreHistory.getScheduleName(),
            scoreHistory.getDate(),
            scoreHistory.getScoreType().getScore()
        );
    }
}
