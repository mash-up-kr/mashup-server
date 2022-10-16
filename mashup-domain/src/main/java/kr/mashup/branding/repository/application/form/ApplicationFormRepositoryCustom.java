package kr.mashup.branding.repository.application.form;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.domain.generation.Generation;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ApplicationFormRepositoryCustom {
    Page<ApplicationForm> findByApplicationFormQueryVo(Generation generation, ApplicationFormQueryVo applicationFormQueryVo);

    List<ApplicationForm> findByTeam(Long teamId);

    Optional<ApplicationForm> findByApplicationForm(Long applicationFormId);
}
/**
 * applicationForm 연관관계
 * many to one: team
 * one to many: questions
 */