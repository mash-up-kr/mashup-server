package kr.mashup.branding.domain.application.form;

import java.util.List;

public interface ApplicationFormService {
    ApplicationForm create(CreateApplicationFormVo createApplicationFormVo);

    ApplicationForm update(Long teamId, Long applicationFormId, UpdateApplicationFormVo updateApplicationFormVo);

    void delete(Long teamId, Long applicationFormId);

    ApplicationForm getApplicationFormById(Long applicationFormId);

    List<ApplicationForm> getApplicationFormsByTeamId(Long teamId);
}
