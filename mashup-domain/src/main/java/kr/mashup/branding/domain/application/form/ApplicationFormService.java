package kr.mashup.branding.domain.application.form;

public interface ApplicationFormService {
    ApplicationForm createApplicationForm(CreateApplicationFormVo createApplicationFormVo);

    ApplicationForm getApplicationFormById(Long applicationFormId);

    ApplicationForm getApplicationFormByTeamId(Long teamId);
}
