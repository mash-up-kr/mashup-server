package kr.mashup.branding.facade.application.form;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.UpdateApplicationFormVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationFormFacadeServiceImpl implements ApplicationFormFacadeService {
    private final ApplicationFormService applicationFormService;

    @Override
    public ApplicationForm create(CreateApplicationFormVo createApplicationFormVo) {
        return applicationFormService.create(createApplicationFormVo);
    }

    @Override
    public ApplicationForm update(
        Long applicationFormId,
        UpdateApplicationFormVo updateApplicationFormVo
    ) {
        return applicationFormService.update(applicationFormId, updateApplicationFormVo);
    }

    @Override
    public Page<ApplicationForm> getApplicationForms(ApplicationFormQueryVo applicationFormQueryVo) {
        return applicationFormService.getApplicationForms(applicationFormQueryVo);
    }

    @Override
    public ApplicationForm getApplicationForm(Long applicationFormId) {
        return applicationFormService.getApplicationFormById(applicationFormId);
    }

    @Override
    public void delete(Long applicationFormId) {
        applicationFormService.delete(applicationFormId);
    }
}
