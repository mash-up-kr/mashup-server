package kr.mashup.branding.domain.application.form;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {
    List<ApplicationForm> findByTeam_teamId(Long teamId);
}
