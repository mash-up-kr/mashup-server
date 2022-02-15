package kr.mashup.branding.ui.application;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResponse {
    private Long answerId;
    private Long questionId;
    private String content;
}
