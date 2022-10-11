package kr.mashup.branding.facade.application;

import java.util.List;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantNotFoundException;
import kr.mashup.branding.domain.application.ApplicationCreationRequestInvalidException;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormNotFoundException;
import kr.mashup.branding.domain.team.TeamNotFoundException;
import kr.mashup.branding.service.applicant.ApplicantService;
import kr.mashup.branding.service.application.ApplicationFormService;
import kr.mashup.branding.service.team.TeamService;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.domain.application.ApplicationSubmitRequestVo;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import kr.mashup.branding.domain.application.confirmation.UpdateConfirmationVo;
import kr.mashup.branding.ui.application.UpdateConfirmationRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationFacadeService {
    private final TeamService teamService;
    private final ApplicantService applicantService;
    private final ApplicationService applicationService;
    private final ApplicationFormService applicationFormService;


    /**
     * 각 팀의 지원서 상세페이지 접근시 빈 지원서 생성 또는 기존 지원서 조회
     */
    public Application create(Long applicantId, CreateApplicationVo createApplicationVo) {

        final Applicant applicant;
        try {
            applicant = applicantService.getApplicant(applicantId);
        } catch (ApplicantNotFoundException e) {
            throw new ApplicationCreationRequestInvalidException("Applicant not found. applicantId: " + applicantId, e);
        }

        try {
            teamService.getTeam(createApplicationVo.getTeamId());
        } catch (TeamNotFoundException e) {
            throw new ApplicationCreationRequestInvalidException(
                "Team not found. teamId: " + createApplicationVo.getTeamId(), e);
        }

        final ApplicationForm applicationForm;
        try {
            applicationForm = applicationFormService.getApplicationFormsByTeamId(createApplicationVo.getTeamId())
                .stream()
                .findFirst()
                .orElseThrow(ApplicationFormNotFoundException::new);
        } catch (ApplicationFormNotFoundException e) {
            throw new ApplicationCreationRequestInvalidException(
                "ApplicationForm not found. teamId: " + createApplicationVo.getTeamId(), e);
        }

        return applicationService.create(applicantId, applicant, applicationForm);
    }

    /**
     * 지원서 임시저장
     */
    public Application update(Long applicantId, Long applicationId, UpdateApplicationVo updateApplicationVo) {
        return applicationService.update(applicantId, applicationId, updateApplicationVo);
    }

    /**
     * 지원서 제출
     */
    public Application submit(
        Long applicantId,
        Long applicationId,
        ApplicationSubmitRequestVo applicationSubmitRequestVo
    ) {
        return applicationService.submit(applicantId, applicationId, applicationSubmitRequestVo);
    }

    /**
     * 내 지원서 목록 보기
     */
    public List<Application> getApplications(Long applicantId) {
        return applicationService.getApplications(applicantId);
    }

    /**
     * 내 지원서 상세 보기
     */
    public Application getApplication(Long applicantId, Long applicationId) {
        return applicationService.getApplication(applicantId, applicationId);
    }

    /**
     * 인터뷰, 최종합격에 대한 지원자 응답
     */
    public Application updateConfirm(Long applicantId, Long applicationId,
                                     UpdateConfirmationRequest updateRequest) {
        return applicationService.updateConfirmationFromApplicant(
            applicantId,
            UpdateConfirmationVo.of(applicationId, updateRequest.getStatus(), updateRequest.getRejectionReason()));
    }

    /**
     * (Test용) 인터뷰, 최종합격에 대한 지원자 응답
     */
    public Application updateConfirmForTest(Long applicantId, Long applicationId,
                                            UpdateConfirmationRequest updateRequest) {
        return applicationService.updateConfirmationForTest(
            applicantId,
            UpdateConfirmationVo.of(applicationId, updateRequest.getStatus(), updateRequest.getRejectionReason()));
    }
}
