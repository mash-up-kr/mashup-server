package kr.mashup.branding.facade.application;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.service.notification.NotificationService;
import kr.mashup.branding.ui.application.vo.ApplicationDetailResponse;
import kr.mashup.branding.ui.application.vo.ApplicationSimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminApplicationFacadeService {

    private final AdminMemberService adminMemberService;
    private final ApplicationService applicationService;
    private final NotificationService notificationService;

    public Page<ApplicationSimpleResponse> getApplications(Long adminMemberId, ApplicationQueryVo applicationQueryVo) {
        return applicationService
            .getApplications(adminMemberId, applicationQueryVo)
            .map(ApplicationSimpleResponse::from);
    }

    public ApplicationDetailResponse getApplicationDetail(Long adminMemberId, Long applicationId) {

        AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        Application application = applicationService.getApplicationFromAdmin(adminMember, applicationId);
        List<SmsRequest> smsRequests = notificationService.getSmsRequestsByApplicantId(
            application.getApplicant().getApplicantId());

        return ApplicationDetailResponse.of(application, smsRequests);
    }

    public List<ApplicationSimpleResponse> updateResults(
        Long adminMemberId,
        List<UpdateApplicationResultVo> updateApplicationResultVoList
    ) {

        AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);

        return updateApplicationResultVoList.stream()
            .map(it -> {
                try {
                    return applicationService.updateResult(adminMember, it);
                } catch (Exception e) {
                    log.warn("Failed to update result. applicationId: {}", it.getApplicationId(), e);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .map(ApplicationSimpleResponse::from)
            .collect(Collectors.toList());
    }

    public ApplicationSimpleResponse updateResult(Long adminMemberId, UpdateApplicationResultVo updateApplicationResultVo) {
        AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        Application application = applicationService.updateResult(adminMember, updateApplicationResultVo);

        return ApplicationSimpleResponse.from(application);
    }
}
