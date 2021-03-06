package kr.mashup.branding.facade.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.service.notification.sms.SmsRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationFacadeServiceImpl implements ApplicationFacadeService {
    private final ApplicationService applicationService;
    private final SmsRequestService smsRequestService;

    @Override
    public Page<Application> getApplications(Long adminMemberId, ApplicationQueryVo applicationQueryVo) {
        return applicationService.getApplications(adminMemberId, applicationQueryVo);
    }

    @Override
    public ApplicationDetailVo getApplicationDetail(Long adminMemberId, Long applicationId) {
        Application application = applicationService.getApplicationFromAdmin(adminMemberId, applicationId);
        List<SmsRequest> smsRequests = smsRequestService.getSmsRequestsByApplicantId(
            application.getApplicant().getApplicantId());
        return ApplicationDetailVo.of(application, smsRequests);
    }

    @Override
    public List<Application> updateResults(
        Long adminMemberId,
        List<UpdateApplicationResultVo> updateApplicationResultVoList
    ) {
        return updateApplicationResultVoList.stream()
            .map(it -> {
                try {
                    return applicationService.updateResult(adminMemberId, it);
                } catch (Exception e) {
                    log.warn("Failed to update result. applicationId: {}", it.getApplicationId(), e);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public Application updateResult(Long adminMemberId, UpdateApplicationResultVo updateApplicationResultVo) {
        return applicationService.updateResult(adminMemberId, updateApplicationResultVo);
    }
}
