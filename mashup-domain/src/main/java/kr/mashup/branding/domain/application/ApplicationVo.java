package kr.mashup.branding.domain.application;

import lombok.Value;

import java.util.List;

@Value
public class ApplicationVo {
    Long applicationId;
    List<AnswerVo> answerVoList;
}
