package kr.mashup.branding.ui.scorehistory.request;

import java.time.LocalDate;

import com.sun.istack.NotNull;

import kr.mashup.branding.domain.scorehistory.ScoreType;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class ScoreHistoryCreateRequest {

    @NotNull
    ScoreType scoreType;

    @NotNull
    private String scheduleName;

    @NotNull
    private LocalDate date;

    @NotNull
    private Long generationId;

    @NotNull
    private Long memberId;
}
