package kr.mashup.branding.facade.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.result.ApplicationResultStatus;
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

    @Override
    public List<Application> update(List<Long> applicationIds, ApplicationResultStatus status) {
        return applicationIds.stream()
            .map(it -> {
                try {
                    return applicationService.updateResult(it, status);
                } catch (Exception e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
