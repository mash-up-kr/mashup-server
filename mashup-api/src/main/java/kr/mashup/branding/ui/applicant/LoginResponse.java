package kr.mashup.branding.ui.applicant;

import kr.mashup.branding.domain.applicant.ApplicantStatus;
import lombok.Data;

@Data
public class LoginResponse {
    private final String accessToken;
    private final Long applicantId;
    private final String email;
    private final ApplicantStatus status;
}
