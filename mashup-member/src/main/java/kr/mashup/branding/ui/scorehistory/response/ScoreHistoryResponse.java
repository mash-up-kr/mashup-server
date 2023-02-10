package kr.mashup.branding.ui.scorehistory.response;

import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class ScoreHistoryResponse {

    Integer generationNumber;
    Double totalScore;
    List<ScoreDetail> scoreDetails;

    @Getter
    @Value(staticConstructor = "of")
    public static class ScoreDetail {
        String scoreType;
        String scoreName;
        Double score;
        Double cumulativeScore;
        LocalDateTime date;
        String scheduleName;
        Boolean isCanceled;
    }
}
