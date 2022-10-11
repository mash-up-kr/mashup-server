package kr.mashup.branding.ui.applicant;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.ui.applicant.vo.ApplicantResponse;
import org.springframework.stereotype.Component;

@Component
public class ApplicantAssembler {
    public ApplicantResponse toApplicantResponse(Applicant applicant) {
        return new ApplicantResponse(
            applicant.getApplicantId(),
            applicant.getEmail(),
            applicant.getName(),
            applicant.getPhoneNumber(),
            applicant.getBirthdate(),
            applicant.getDepartment(),
            applicant.getResidence(),
            applicant.getStatus()
        );
    }
}
