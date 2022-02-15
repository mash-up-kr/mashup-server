package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;
import java.util.List;

import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.ui.applicant.ApplicantResponse;
import kr.mashup.branding.ui.team.TeamResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationResponse {
    private Long applicationId;
    private ApplicantResponse applicant;
    private TeamResponse team;
    private ApplicantConfirmationStatus confirmationStatus;
    private ApplicationStatus status;
    private LocalDateTime submittedAt;
    private List<QuestionResponse> questions;
    private List<AnswerResponse> answers;
    private ApplicationResultResponse result;
    private Boolean privacyPolicyAgreed;
}
