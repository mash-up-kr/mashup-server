package kr.mashup.branding.facade.application.form;

import org.springframework.data.domain.Page;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.UpdateApplicationFormVo;

public interface ApplicationFormFacadeService {
    ApplicationForm create(CreateApplicationFormVo createApplicationFormVo);

    ApplicationForm update(Long applicationFormId, UpdateApplicationFormVo updateApplicationFormVo);

    Page<ApplicationForm> getApplicationForms(ApplicationFormQueryVo applicationFormQueryVo);

    void delete(Long applicationFormId);
}
