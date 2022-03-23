package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.ui.applicant.ApplicantResponse;
import kr.mashup.branding.ui.team.TeamResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationSimpleResponse {
    private Long applicationId;
    private ApplicantResponse applicant;
    private TeamResponse team;
    private ApplicantConfirmationStatus confirmationStatus;
    private ApplicationResultResponse result;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
