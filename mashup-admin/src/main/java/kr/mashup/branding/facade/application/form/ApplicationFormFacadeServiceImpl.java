package kr.mashup.branding.facade.application.form;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.mashup.branding.domain.application.form.ApplicationForm;
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
        Long teamId,
        Long applicationFormId,
        UpdateApplicationFormVo updateApplicationFormVo
    ) {
        return applicationFormService.update(teamId, applicationFormId, updateApplicationFormVo);
    }

    @Override
    public List<ApplicationForm> getApplicationForms(Long teamId, String name, Pageable pageable) {
        if (StringUtils.hasText(name)) {
            return applicationFormService.getApplicationForms(teamId, name, pageable).getContent();
        }
        return applicationFormService.getApplicationForms(teamId, pageable).getContent();
    }

    @Override
    public void delete(Long teamId, Long applicationFormId) {
        applicationFormService.delete(teamId, applicationFormId);
    }
}
