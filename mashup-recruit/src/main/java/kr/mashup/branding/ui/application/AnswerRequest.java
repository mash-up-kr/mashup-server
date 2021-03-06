package kr.mashup.branding.ui.application;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AnswerRequest {
    /**
     * 답변 식별자
     */
    private Long answerId;
    /**
     * 질문 식별자
     */
    private Long questionId;
    /**
     * 답변 내용
     */
    private String content;
}
