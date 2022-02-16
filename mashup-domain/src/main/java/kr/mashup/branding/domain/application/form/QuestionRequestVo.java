package kr.mashup.branding.domain.application.form;

import lombok.Value;

@Value(staticConstructor = "of")
public class QuestionRequestVo {
    String content;
    Integer maxContentLength;
    Boolean required;
    QuestionType questionType;
}
