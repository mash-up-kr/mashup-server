package kr.mashup.branding.ui.application;

import java.util.List;

import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.ui.applicant.ApplicantResponse;
import kr.mashup.branding.ui.team.TeamResponse;
import lombok.Data;

@Data
public class ApplicationResponse {
    private final Long applicationId;
    private final ApplicantResponse applicant;
    private final TeamResponse team;
    private final ApplicantConfirmationStatus confirmationStatus;
    private final ApplicationStatus status;
    private final List<QuestionResponse> questions;
    private final List<AnswerResponse> answers;
    private final ApplicationResultResponse result;
    private final Boolean privacyPolicyAgreed;
}
