package kr.mashup.branding.ui.application;

import lombok.Data;

@Data
public class AnswerRequest {
    private Long questionId;
    private String content;
}
