package kr.mashup.branding.service.applicant;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.JoinRequestVo;

import java.util.Collection;
import java.util.List;

public interface ApplicantService {
    Applicant getApplicant(Long applicantId);

    Applicant join(JoinRequestVo joinRequestVo);

    List<Applicant> getApplicants(Collection<Long> applicantIds);
}
