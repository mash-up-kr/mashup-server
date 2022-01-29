package kr.mashup.branding.domain.application;

import java.util.List;

public interface ApplicationService {
    Application createApplication(CreateApplicationVo createApplicationVo);
    Application updateApplication(UpdateApplicationVo updateApplicationVo);
    List<Application> getAllApplication();
    List<Application> getApplications(Long memberId);
    Application getApplication(Long memberId, Long applicationId);
    Application getApplication(Long applicationId);
}
