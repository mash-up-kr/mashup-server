package kr.mashup.branding.domain.applicant;

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

    /**
     * 테스터 조회
     * FIXME: 로그인 기능 추가되면 삭제해야함
     */
    @Override
    public Applicant getTester() {
        return applicantRepository.findAll().stream().findFirst().orElseThrow(ApplicantNotFoundException::new);
    }
}
