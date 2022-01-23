package kr.mashup.branding.domain.application;

import lombok.Value;

import java.util.List;

@Value
public class CreateApplicationVo {
    Long applicationFormId;
    List<CreateAnswerVo> createAnswerVoList;
}
