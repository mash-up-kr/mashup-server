package kr.mashup.branding.facade.application;

import java.util.List;

import org.springframework.data.domain.Pageable;

import kr.mashup.branding.domain.application.Application;

public interface ApplicationFacadeService {
    List<Application> getApplications(String searchWord, Pageable pageable);

    Application getApplication(Long applicationId);
}
