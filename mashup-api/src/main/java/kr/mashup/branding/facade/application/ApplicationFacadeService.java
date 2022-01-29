package kr.mashup.branding.facade.application;

import java.util.List;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;

// TODO: applicant
public interface ApplicationFacadeService {
    Application create(CreateApplicationVo createApplicationVo);

    Application update(Long applicationId, UpdateApplicationVo updateApplicationVo);

    Application submit(Long applicationId);

    List<Application> getApplications(Long applicantId);

    Application getApplication(Long applicantId, Long applicationId);
}
