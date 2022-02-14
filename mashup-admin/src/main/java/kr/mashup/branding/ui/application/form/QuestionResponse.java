package kr.mashup.branding.ui.application.form;

import kr.mashup.branding.domain.application.form.QuestionType;
import lombok.Data;

@Data
public class QuestionResponse {
    private final Long questionId;
    private final String content;
    private final Integer properSize;
    private final Boolean required;
    private final QuestionType questionType;
}
