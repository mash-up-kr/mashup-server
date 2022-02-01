package kr.mashup.branding.facade.application.form;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationFormFacadeServiceImpl implements ApplicationFormFacadeService {
    private final ApplicationFormService applicationFormService;

    @Override
    public ApplicationForm create(CreateApplicationFormVo createApplicationFormVo) {
        return applicationFormService.createApplicationForm(createApplicationFormVo);
    }

    @Override
    public List<ApplicationForm> getApplicationForms(Long teamId) {
        return Collections.emptyList();
    }
}
