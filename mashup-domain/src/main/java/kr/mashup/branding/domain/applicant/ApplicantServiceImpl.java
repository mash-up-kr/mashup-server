package kr.mashup.branding.domain.applicant;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {
    private final ApplicantRepository applicantRepository;

    @Override
    public Applicant getApplicant(Long applicantId) {
        Assert.notNull(applicantId, "'applicantId' must not be null");
        return applicantRepository.findById(applicantId)
            .orElseThrow(ApplicantNotFoundException::new);
    }

    @Transactional
    @Override
    public Applicant join(JoinRequestVo joinRequestVo) {
        Assert.notNull(joinRequestVo, "'joinRequestVo' must not be null");
        return applicantRepository.findByGoogleUserId(joinRequestVo.getGoogleUserId())
            .orElseGet(() -> applicantRepository.save(
                Applicant.of(joinRequestVo.getEmail(), joinRequestVo.getGoogleUserId()))
            );
    }

    @Override
    public List<Applicant> getApplicants(Collection<Long> applicantIds) {
        return applicantRepository.findByApplicantIdIn(applicantIds);
    }
}
