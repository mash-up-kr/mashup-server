package kr.mashup.branding.facade.application;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationFacadeServiceImpl implements ApplicationFacadeService {
    private final ApplicationService applicationService;

    @Override
    public List<Application> getApplications(String searchWord, Pageable pageable) {
        return applicationService.getApplications(searchWord, pageable).getContent();
    }

    @Override
    public Application getApplication(Long applicationId) {
        return applicationService.getApplication(applicationId);
    }
}
