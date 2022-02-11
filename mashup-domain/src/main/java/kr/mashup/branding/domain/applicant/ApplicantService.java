package kr.mashup.branding.domain.applicant;

public interface ApplicantService {
    Applicant getApplicant(Long applicantId);

    Applicant getTester();

    Applicant join(LoginRequestVo loginRequestVo);
}
