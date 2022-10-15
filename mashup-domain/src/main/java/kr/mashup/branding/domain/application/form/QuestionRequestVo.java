package kr.mashup.branding.domain.application.form;

import lombok.Value;

@Value(staticConstructor = "of")
public class QuestionRequestVo {
    String content;
    Integer maxContentLength;
    String description;
    Boolean required;
    QuestionType questionType;

}
