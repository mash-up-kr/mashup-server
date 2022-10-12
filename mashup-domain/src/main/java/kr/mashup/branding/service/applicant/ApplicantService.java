package kr.mashup.branding.service.applicant;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantNotFoundException;
import kr.mashup.branding.domain.applicant.JoinRequestVo;
import kr.mashup.branding.repository.applicant.ApplicantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public Applicant getApplicant(Long applicantId) {

        Assert.notNull(applicantId, "'applicantId' must not be null");

        return applicantRepository.findById(applicantId)
            .orElseThrow(ApplicantNotFoundException::new);
    }

    @Transactional
    public Applicant join(JoinRequestVo joinRequestVo) {
        Assert.notNull(joinRequestVo, "'joinRequestVo' must not be null");

        final Applicant applicant
            = applicantRepository
            .findByGoogleUserId(joinRequestVo.getGoogleUserId())
            .orElseGet(() -> applicantRepository.save(Applicant.of(joinRequestVo.getEmail(), joinRequestVo.getGoogleUserId())));

        return applicant;
    }

    public List<Applicant> getApplicants(Collection<Long> applicantIds) {
        return applicantRepository.findByApplicantIdIn(applicantIds);
    }
}
