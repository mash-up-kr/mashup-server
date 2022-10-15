package kr.mashup.branding.ui.scorehistory.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
public class ScoreAddRequest {
    private ScoreType scoreType;
    private Long memberId;
    private Integer generationNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;
    private String memo;
    private String name;
}
