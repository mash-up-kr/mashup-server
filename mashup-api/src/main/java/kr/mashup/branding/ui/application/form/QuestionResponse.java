package kr.mashup.branding.ui.application.form;

import lombok.Data;

@Data
public class QuestionResponse {
    private final Long questionId;
    private final String content;
    private final Integer properSize;
    private final Boolean required;
    private final String questionType;
}
