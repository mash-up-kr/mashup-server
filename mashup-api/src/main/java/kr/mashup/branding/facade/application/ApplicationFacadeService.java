package kr.mashup.branding.facade.application;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;

public interface ApplicationFacadeService {
    Application create(CreateApplicationVo createApplicationVo);
    Application update(UpdateApplicationVo updateApplicationVo);
    Application submit();
    Application getApplications();
    Application getApplication(Long applicationId);
}
