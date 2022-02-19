package kr.mashup.branding.ui.applicant;

import kr.mashup.branding.domain.applicant.ApplicantStatus;
import lombok.Data;

@Data
public class ApplicantResponse {
    private final Long applicantId;
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final ApplicantStatus status;
}
