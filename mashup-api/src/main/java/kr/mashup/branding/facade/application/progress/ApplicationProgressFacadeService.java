package kr.mashup.branding.facade.application.progress;

import kr.mashup.branding.domain.application.progress.ApplicationProgress;
import kr.mashup.branding.ui.application.progress.UpdateApplicationProgressRequest;

public interface ApplicationProgressFacadeService {
    ApplicationProgress getApplicationProgress(Long applicationId);

    ApplicationProgress updateApplicationProgress(Long applicationId, UpdateApplicationProgressRequest updateRequest);
}
