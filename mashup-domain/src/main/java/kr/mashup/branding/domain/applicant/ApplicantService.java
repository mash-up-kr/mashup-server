package kr.mashup.branding.domain.applicant;

import java.util.Collection;
import java.util.List;

public interface ApplicantService {
    Applicant getApplicant(Long applicantId);

    Applicant join(JoinRequestVo joinRequestVo);

    List<Applicant> getApplicants(Collection<Long> applicantIds);
}
