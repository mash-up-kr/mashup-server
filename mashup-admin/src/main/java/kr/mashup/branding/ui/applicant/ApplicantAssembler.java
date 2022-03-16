package kr.mashup.branding.ui.applicant;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.applicant.Applicant;

@Component
public class ApplicantAssembler {
    public ApplicantResponse toApplicantResponse(Applicant applicant) {
        return new ApplicantResponse(
            applicant.getApplicantId(),
            applicant.getName(),
            applicant.getEmail(),
            applicant.getPhoneNumber(),
            applicant.getBirthdate(),
            applicant.getDepartment(),
            applicant.getResidence(),
            applicant.getStatus(),
            applicant.getCreatedAt(),
            applicant.getUpdatedAt()
        );
    }
}
