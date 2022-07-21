package kr.mashup.branding.ui.scorehistory.response;

import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDate;

@Getter
@Value(staticConstructor = "of")
public class ScoreHistoryResponse {

    Long scoreHistoryId;

    String scoreName;

    Double score;

    LocalDate date;

    String scheduleName;

    public static ScoreHistoryResponse from(ScoreHistory scoreHistory) {
        return ScoreHistoryResponse.of(
            scoreHistory.getId(),
            scoreHistory.getName(),
            scoreHistory.getScore(),
            scoreHistory.getDate(),
            scoreHistory.getScheduleName()
        );
    }
}
