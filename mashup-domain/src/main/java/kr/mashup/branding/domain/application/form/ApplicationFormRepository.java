package kr.mashup.branding.domain.application.form;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {
    List<ApplicationForm> findByTeam_teamId(Long teamId);

    Page<ApplicationForm> findByTeam_teamId(Long teamId, Pageable pageable);

    Optional<ApplicationForm> findByTeam_teamIdAndApplicationFormId(Long teamId, Long applicationFormId);

    Page<ApplicationForm> findByTeam_teamIdAndNameLike(Long teamId, String name, Pageable pageable);
}
