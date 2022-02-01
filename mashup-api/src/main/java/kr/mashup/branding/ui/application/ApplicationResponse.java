package kr.mashup.branding.ui.application;

import java.util.List;

import lombok.Data;

@Data
public class ApplicationResponse {
    private final Long applicationId;
    private final String applicantName;
    private final String phoneNumber;
    private final String email;
    private final String status;
    private final List<AnswerResponse> answers;
}
