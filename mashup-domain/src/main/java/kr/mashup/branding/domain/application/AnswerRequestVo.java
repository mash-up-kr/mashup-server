package kr.mashup.branding.domain.application;

import lombok.Value;

@Value(staticConstructor = "of")
public class AnswerRequestVo {
    Long answerId;
    Long questionId;
    String content;
}
