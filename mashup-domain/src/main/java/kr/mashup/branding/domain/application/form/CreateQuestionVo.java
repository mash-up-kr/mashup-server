package kr.mashup.branding.domain.application.form;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreateQuestionVo {
    String content;
    Integer properSize;
    QuestionType questionType;
}
