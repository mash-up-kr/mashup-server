package kr.mashup.branding.ui.application;

import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import lombok.Data;

@Data
public class ApplicationResponse {
    private final Long applicationId;
    private ApplicantConfirmationStatus confirmationStatus;
}
