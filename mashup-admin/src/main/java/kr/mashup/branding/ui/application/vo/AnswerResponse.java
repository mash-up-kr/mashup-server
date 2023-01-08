package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.application.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResponse {
    private Long answerId;
    private Long questionId;
    private String content;

    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(
            answer.getAnswerId(),
            answer.getQuestion().getQuestionId(),
            answer.getContent()
        );
    }
}
