package kr.mashup.branding.domain.application.form;

import java.util.List;

public interface ApplicationFormService {
    ApplicationForm createApplicationForm(CreateApplicationFormVo createApplicationFormVo);

    ApplicationForm getApplicationFormById(Long applicationFormId);

    List<ApplicationForm> getApplicationFormsByTeamId(Long teamId);
}
