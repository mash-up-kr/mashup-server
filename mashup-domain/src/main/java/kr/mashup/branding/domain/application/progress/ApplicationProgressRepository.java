package kr.mashup.branding.domain.application.progress;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationProgressRepository extends JpaRepository<ApplicationProgress, Long> {
    Optional<ApplicationProgress> findByApplicationProgress_applicationProgressId(Long applicationProgressId);
}
