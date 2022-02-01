package kr.mashup.branding.ui.application.form;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateApplicationFormRequest {
    private List<CreateQuestionRequest> questions;
    private String name;
}
