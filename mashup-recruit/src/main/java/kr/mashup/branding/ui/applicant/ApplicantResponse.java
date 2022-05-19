package kr.mashup.branding.ui.applicant;

import kr.mashup.branding.domain.applicant.ApplicantStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicantResponse {
    private final Long applicantId;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final LocalDate birthdate;
    private final String department;
    private final String residence;
    private final ApplicantStatus status;
}
