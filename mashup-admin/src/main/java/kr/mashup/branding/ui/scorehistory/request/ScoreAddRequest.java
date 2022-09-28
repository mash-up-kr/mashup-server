package kr.mashup.branding.ui.scorehistory.request;

import kr.mashup.branding.domain.scorehistory.ScoreType;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class ScoreAddRequest {
    private ScoreType scoreType;
    private Long memberId;
    private Integer generationNumber;
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")
    private String date;
    private String memo;
    private String name;
}
