package kr.mashup.branding.facade.application.form;

import java.util.List;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;

public interface ApplicationFormFacadeService {
    ApplicationForm create(CreateApplicationFormVo createApplicationFormVo);

    List<ApplicationForm> getApplicationForms(Long teamId);
}
