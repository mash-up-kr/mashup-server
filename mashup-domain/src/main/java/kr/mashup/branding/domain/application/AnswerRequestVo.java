package kr.mashup.branding.domain.application;

import lombok.Value;

@Value
public class AnswerRequestVo {
    Long questionId;
    String content;
}
