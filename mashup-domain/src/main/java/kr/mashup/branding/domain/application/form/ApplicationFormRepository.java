package kr.mashup.branding.domain.application.form;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {
    Optional<ApplicationForm> findByTeam_teamId(Long teamId);
}
