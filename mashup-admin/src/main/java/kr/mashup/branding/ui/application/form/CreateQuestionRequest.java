package kr.mashup.branding.ui.application.form;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateQuestionRequest {
    private String content;
    private Integer properSize;
    private Boolean required;
    private String questionType;
}
