package kr.mashup.branding.facade.application.form;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.UpdateApplicationFormVo;
import kr.mashup.branding.facade.ProfileFacadeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationFormFacadeServiceImpl implements ApplicationFormFacadeService {
    private final ApplicationFormService applicationFormService;
    private final ApplicationService applicationService;
    private final ProfileFacadeService profileFacadeService;

    @Override
    public ApplicationForm create(Long adminMemberId, CreateApplicationFormVo createApplicationFormVo) {
        return applicationFormService.create(adminMemberId, createApplicationFormVo);
    }

    @Override
    public ApplicationForm update(
        Long adminMemberId,
        Long applicationFormId,
        UpdateApplicationFormVo updateApplicationFormVo
    ) {
        return applicationFormService.update(adminMemberId, applicationFormId, updateApplicationFormVo);
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
    public void delete(Long adminMemberId, Long applicationFormId) {
        // 개발 환경에서는 설문지 삭제 시도시 지원서를 모두 삭제 후 설문지까지 삭제 한다.
        if (profileFacadeService.isLocal() || profileFacadeService.isDevelop()) {
            applicationService.deleteByApplicationFormId(applicationFormId);
        }
        applicationFormService.delete(adminMemberId, applicationFormId);
    }
}
