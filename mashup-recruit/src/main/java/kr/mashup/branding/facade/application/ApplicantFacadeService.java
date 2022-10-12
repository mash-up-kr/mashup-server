package kr.mashup.branding.facade.application;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.service.applicant.ApplicantService;
import kr.mashup.branding.ui.applicant.vo.ApplicantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicantFacadeService {
    private final ApplicantService applicantService;

    @Transactional(readOnly = true)
    public ApplicantResponse getApplicant(Long applicantId) {
        final Applicant applicant = applicantService.getApplicant(applicantId);
        return ApplicantResponse.from(applicant);
    }
}
