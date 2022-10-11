package kr.mashup.branding.ui.application.vo;

import lombok.Data;

@Data
public class AnswerResponse {
    private final Long answerId;
    private final Long questionId;
    private final String content;
}
