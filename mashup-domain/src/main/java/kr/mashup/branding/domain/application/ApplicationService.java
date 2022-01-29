package kr.mashup.branding.domain.application;

import java.util.List;

public interface ApplicationService {
    Application create(CreateApplicationVo createApplicationVo);
    Application update(Long applicationId, UpdateApplicationVo updateApplicationVo);
    Application submit(Long applicationId);
    List<Application> getApplications(Long applicantId);
    Application getApplication(Long applicantId, Long applicationId);
}
