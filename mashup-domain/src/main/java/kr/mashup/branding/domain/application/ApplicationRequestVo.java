package kr.mashup.branding.domain.application;

import lombok.Value;

import java.util.List;

@Value
public class ApplicationRequestVo {
    List<AnswerRequestVo> answerRequestVos;
}
