package kr.mashup.branding.ui.application.form.vo;

import kr.mashup.branding.domain.application.form.QuestionRequestVo;
import kr.mashup.branding.domain.application.form.QuestionType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionRequest {
    private String content;
    private Integer maxContentLength;
    private String description;
    private Boolean required;
    private QuestionType questionType;

    public QuestionRequestVo to(){
        return QuestionRequestVo.of(content,maxContentLength, description, required, questionType);
    }
}
