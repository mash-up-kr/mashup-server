package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.ui.applicant.ApplicantResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationSimpleResponse {
    private Long applicationId;
    private ApplicantResponse applicant;
    private ApplicantConfirmationStatus confirmationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
