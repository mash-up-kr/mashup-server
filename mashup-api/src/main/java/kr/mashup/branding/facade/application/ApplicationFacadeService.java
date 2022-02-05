package kr.mashup.branding.facade.application;

import java.util.List;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;

public interface ApplicationFacadeService {
    Application create(Long applicantId, CreateApplicationVo createApplicationVo);

    Application update(Long applicantId, Long applicationId, UpdateApplicationVo updateApplicationVo);

    Application submit(Long applicantId, Long applicationId);

    List<Application> getApplications(Long applicantId);

    Application getApplication(Long applicantId, Long applicationId);
}
