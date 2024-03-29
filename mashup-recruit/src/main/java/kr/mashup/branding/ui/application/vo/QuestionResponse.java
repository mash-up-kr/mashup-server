package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.application.form.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionResponse {
    private Long questionId;
    private String content;
    private Integer maxContentLength;
    private String description;
    private Boolean required;
    private QuestionType questionType;

    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
            question.getQuestionId(),
            question.getContent(),
            question.getMaxContentLength(),
            question.getDescription(),
            question.getRequired(),
            question.getQuestionType()
        );
    }

}
