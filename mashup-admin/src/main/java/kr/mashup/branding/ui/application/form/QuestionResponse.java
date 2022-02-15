package kr.mashup.branding.ui.application.form;

import kr.mashup.branding.domain.application.form.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionResponse {
    private Long questionId;
    private String content;
    private Integer maxContentSize;
    private Boolean required;
    private QuestionType questionType;
}
