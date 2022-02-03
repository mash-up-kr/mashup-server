package kr.mashup.branding.ui.application.form;

import java.util.List;

import lombok.Data;

@Data
public class ApplicationFormResponse {
    private final Long applicationFormId;
    private final String name;
    private final List<QuestionResponse> questions;
}
