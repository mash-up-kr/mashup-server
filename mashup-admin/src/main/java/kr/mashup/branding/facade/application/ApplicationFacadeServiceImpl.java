package kr.mashup.branding.facade.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public List<Application> updateResults(List<UpdateApplicationResultsVo> updateApplicationResultsVoList) {
        return updateApplicationResultsVoList.stream()
            .map(it -> {
                try {
                    return applicationService.updateResult(it);
                } catch (Exception e) {
                    log.warn("Failed to update result. applicationId: {}", it.getApplicationId(), e);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public Application updateResult(
        Long adminMemberId,
        Long applicationId,
        UpdateApplicationResultVo updateApplicationResultVo
    ) {
        return applicationService.updateResult(adminMemberId, applicationId, updateApplicationResultVo);
    }
}
