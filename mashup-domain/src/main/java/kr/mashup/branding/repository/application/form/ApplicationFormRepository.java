package kr.mashup.branding.repository.application.form;

import java.util.List;
import java.util.Optional;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFormRepository
    extends JpaRepository<ApplicationForm, Long>, ApplicationFormRepositoryCustom {

    boolean existsByTeam_teamId(Long teamId);

    List<ApplicationForm> findByTeam_teamId(Long teamId);

    Page<ApplicationForm> findByTeam_teamId(Long teamId, Pageable pageable);

    Optional<ApplicationForm> findByApplicationFormId(Long applicationFormId);

    boolean existsByNameLike(String name);

    long countByNameLike(String name);
}
/**
 * applicationForm 연관관계
 * many to one: team
 * one to many: questions
 */