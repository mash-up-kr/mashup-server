package kr.mashup.branding.service.application;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.ApplicationSubmitRequestVo;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import kr.mashup.branding.domain.application.confirmation.UpdateConfirmationVo;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface  ApplicationService {
    Application create(Long applicantId, CreateApplicationVo createApplicationVo);

    Application update(Long applicantId, Long applicationId, UpdateApplicationVo updateApplicationVo);

    Application submit(Long applicantId, Long applicationId,
                       ApplicationSubmitRequestVo applicationSubmitRequestVo);

    Application updateResult(Long adminMemberId, UpdateApplicationResultVo updateApplicationResultVo);

    Application updateConfirmationFromApplicant(Long applicantId, UpdateConfirmationVo updateConfirmationVo);

    Application updateConfirmationForTest(Long applicantId, UpdateConfirmationVo updateConfirmationVo);

    List<Application> getApplications(Long applicantId);

    List<Application> getApplicationsByApplicationStatusAndEventName(ApplicationStatus status, String eventName);

    Application getApplication(Long applicantId, Long applicationId);

    Application getApplicationFromAdmin(Long adminMemberId, Long applicationId);

    Page<Application> getApplications(Long adminMemberId, ApplicationQueryVo applicationQueryVo);

    void updateApplicationResult(Application application);

    void deleteByApplicationFormId(Long applicationFormId);

    void delete(Long applicationId);
}
