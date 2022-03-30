package kr.mashup.branding.domain.application;

import java.util.List;

import org.springframework.data.domain.Page;

import kr.mashup.branding.domain.application.confirmation.UpdateConfirmationVo;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;

public interface  ApplicationService {
    Application create(Long applicantId, CreateApplicationVo createApplicationVo);

    Application update(Long applicantId, Long applicationId, UpdateApplicationVo updateApplicationVo);

    Application submit(Long applicantId, Long applicationId,
                       ApplicationSubmitRequestVo applicationSubmitRequestVo);

    Application updateResult(Long adminMemberId, UpdateApplicationResultVo updateApplicationResultVo);

    Application updateConfirmationFromApplicant(Long applicantId, UpdateConfirmationVo updateConfirmationVo);

    Application updateConfirmationForTest(Long applicantId, UpdateConfirmationVo updateConfirmationVo);

    List<Application> getApplications(Long applicantId);

    Application getApplication(Long applicantId, Long applicationId);

    Application getApplicationFromAdmin(Long adminMemberId, Long applicationId);

    Page<Application> getApplications(Long adminMemberId, ApplicationQueryVo applicationQueryVo);

    void updateApplicationResult(Application application);

    void deleteByApplicationFormId(Long applicationFormId);

    void delete(Long applicationId);
}
