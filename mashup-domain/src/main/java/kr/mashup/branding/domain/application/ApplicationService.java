package kr.mashup.branding.domain.application;

import java.util.List;

interface ApplicationService {
    Application createApplication(CreateApplicationVo createApplicationVo);
    List<Application> getAllApplication();
    List<Application> getApplications(Long memberId);
    Application getApplication(Long memberId, Long applicationId);
    Application getApplication(Long applicationId);
}
