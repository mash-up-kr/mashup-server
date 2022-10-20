package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.application.Answer;
import lombok.Data;

@Data
public class AnswerResponse {
    private final Long answerId;
    private final Long questionId;
    private final String content;

    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(
            answer.getAnswerId(),
            answer.getQuestion().getQuestionId(),
            answer.getContent()
        );
    }
}
