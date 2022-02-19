package kr.mashup.branding.domain.application.form;

import java.util.List;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreateApplicationFormVo {
    Long teamId;
    List<QuestionRequestVo> questionRequestVoList;
    String name;
}
