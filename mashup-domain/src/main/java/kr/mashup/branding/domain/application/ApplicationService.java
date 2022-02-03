package kr.mashup.branding.domain.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;

public interface ApplicationService {
    Application create(CreateApplicationVo createApplicationVo);

    Application update(Long applicationId, UpdateApplicationVo updateApplicationVo);

    Application submit(Long applicationId);

    Application updateResult(UpdateApplicationResultVo updateApplicationResultVo);

    List<Application> getApplications(Long applicantId);

    Application getApplication(Long applicantId, Long applicationId);

    Application getApplication(Long applicationId);

    Page<Application> getApplications(String searchWord, Pageable pageable);
}
