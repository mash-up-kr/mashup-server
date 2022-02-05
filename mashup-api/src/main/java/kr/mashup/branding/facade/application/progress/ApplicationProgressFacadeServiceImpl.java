package kr.mashup.branding.facade.application.progress;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.progress.ApplicationProgress;
import kr.mashup.branding.domain.application.progress.ApplicationProgressService;
import kr.mashup.branding.domain.application.progress.UpdateApplicationProgressVo;
import kr.mashup.branding.ui.application.progress.UpdateApplicationProgressRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApplicationProgressFacadeServiceImpl implements ApplicationProgressFacadeService {

    private final ApplicationService applicationService;
    private final ApplicationProgressService applicationProgressService;

    @Override
    public ApplicationProgress getApplicationProgress(Long applicationId) {
        return applicationProgressService.getByApplicationId(applicationId);
    }

    @Override
    public ApplicationProgress updateApplicationProgress(Long applicationId,
        UpdateApplicationProgressRequest updateRequest) {
        return applicationService.updateProgressFromApplicant(
            UpdateApplicationProgressVo.of(applicationId, updateRequest.getStatus()));
    }
}
