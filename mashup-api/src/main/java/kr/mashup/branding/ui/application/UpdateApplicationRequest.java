package kr.mashup.branding.ui.application;

import java.util.List;

import lombok.Data;

@Data
public class UpdateApplicationRequest {
    private final List<AnswerRequest> answers;
}
