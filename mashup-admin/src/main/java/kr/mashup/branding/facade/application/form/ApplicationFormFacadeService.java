package kr.mashup.branding.facade.application.form;

import java.util.List;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.UpdateApplicationFormVo;

public interface ApplicationFormFacadeService {
    ApplicationForm create(CreateApplicationFormVo createApplicationFormVo);

    ApplicationForm update(Long teamId, Long applicationFormId, UpdateApplicationFormVo updateApplicationFormVo);

    List<ApplicationForm> getApplicationForms(Long teamId);

    void delete(Long teamId, Long applicationFormId);
}
