package kr.mashup.branding.ui.application.form;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionRequest {
    private String content;
    private Integer maxContentSize;
    private Boolean required;
    private String questionType;
}
