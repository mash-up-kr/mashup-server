package kr.mashup.branding.ui.application.form.vo;

import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.QuestionRequestVo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateApplicationFormRequest {
    private Long teamId;
    private List<QuestionRequest> questions;
    private String name;

    public CreateApplicationFormVo toVo(){
        List<QuestionRequestVo> questionVos = questions.stream().map(QuestionRequest::to).collect(Collectors.toList());
        return CreateApplicationFormVo.of(questionVos, name);
    }
}
