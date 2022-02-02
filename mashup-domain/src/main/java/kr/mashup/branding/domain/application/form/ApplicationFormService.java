package kr.mashup.branding.domain.application.form;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface ApplicationFormService {
    ApplicationForm create(CreateApplicationFormVo createApplicationFormVo);

    ApplicationForm update(Long teamId, Long applicationFormId, UpdateApplicationFormVo updateApplicationFormVo);

    void delete(Long teamId, Long applicationFormId);

    ApplicationForm getApplicationFormById(Long applicationFormId);

    List<ApplicationForm> getApplicationFormsByTeamId(Long teamId);

    List<ApplicationForm> getApplicationForms(Long teamId, String keyword, Pageable pageable);
}
