package kr.mashup.branding.domain.application.form;

import lombok.Value;

import java.util.List;

@Value
public class CreateApplicationFormVo {
    Long teamId;
    List<CreateQuestionVo> createQuestionVoList;
}
