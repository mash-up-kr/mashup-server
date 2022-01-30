package kr.mashup.branding.ui.application;

import lombok.Data;

@Data
public class AnswerRequest {
    /**
     * 질문 식별자
     */
    private Long questionId;
    /**
     * 답변 내용
     */
    private String content;
}
