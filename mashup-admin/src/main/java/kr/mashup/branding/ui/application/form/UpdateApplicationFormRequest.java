package kr.mashup.branding.ui.application.form;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationFormRequest {
    private List<QuestionRequest> questions;
    private String name;
}
