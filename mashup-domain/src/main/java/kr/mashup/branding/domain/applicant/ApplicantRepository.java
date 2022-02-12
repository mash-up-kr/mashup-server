package kr.mashup.branding.domain.applicant;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByGoogleUserId(String googleUserId);
}
