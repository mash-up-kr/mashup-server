package kr.mashup.branding.ui.application;

import java.util.List;

import kr.mashup.branding.ui.applicant.ApplicantResponse;
import lombok.Data;

@Data
public class ApplicationResponse {
    private final Long applicationId;
    private final ApplicantResponse applicant;
    private final String status;
    private final List<AnswerResponse> answers;
    private final ApplicationResultResponse result;
}
