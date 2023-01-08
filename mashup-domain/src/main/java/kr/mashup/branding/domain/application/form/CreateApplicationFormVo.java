package kr.mashup.branding.domain.application.form;

import java.util.List;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreateApplicationFormVo {
    List<QuestionRequestVo> questionRequestVoList;
    String name;
}
