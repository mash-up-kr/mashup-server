package kr.mashup.branding.ui.application.form.vo;

import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.application.form.QuestionRequestVo;
import kr.mashup.branding.domain.application.form.UpdateApplicationFormVo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationFormRequest {
    private List<QuestionRequest> questions;
    private String name;

    public UpdateApplicationFormVo toVo(){
        List<QuestionRequestVo> questionRequestVos = questions
            .stream()
            .map(QuestionRequest::to)
            .collect(Collectors.toList());
        return UpdateApplicationFormVo.of(questionRequestVos,name);
    }
}
