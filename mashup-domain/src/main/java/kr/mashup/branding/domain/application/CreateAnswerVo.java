package kr.mashup.branding.domain.application;

import lombok.Value;

@Value
public class CreateAnswerVo {
    Long questionId;
    String content;
}
