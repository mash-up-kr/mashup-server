package kr.mashup.branding.ui.scorehistory.request;

import com.sun.istack.NotNull;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScoreHistoryCreateRequest {

    @NotNull
    private ScoreType scoreType;

    /**
     * ScoreType.ETC 인 경우: scoreName, score 입력
     */
    private String scoreName;

    private Double score;

    @NotNull
    private String scheduleName;

    @NotNull
    private LocalDate date;

    @NotNull
    private Long generationId;

    @NotNull
    private Long memberId;
}
