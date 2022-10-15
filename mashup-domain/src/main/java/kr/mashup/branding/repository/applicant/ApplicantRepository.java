package kr.mashup.branding.repository.applicant;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import kr.mashup.branding.domain.applicant.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByGoogleUserId(String googleUserId);

    List<Applicant> findByApplicantIdIn(Collection<Long> applicantIds);
}
/**
 * Applicant 연관관계
 * 없음
 */