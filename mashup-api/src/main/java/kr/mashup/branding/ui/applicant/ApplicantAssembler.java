package kr.mashup.branding.ui.applicant;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.applicant.Applicant;

@Component
public class ApplicantAssembler {
    public ApplicantResponse toApplicationResponse(Applicant applicant) {
        return new ApplicantResponse(
            applicant.getApplicantId(),
            applicant.getName(),
            applicant.getPhoneNumber(),
            applicant.getEmail(),
            applicant.getStatus().name()
        );
    }
}
