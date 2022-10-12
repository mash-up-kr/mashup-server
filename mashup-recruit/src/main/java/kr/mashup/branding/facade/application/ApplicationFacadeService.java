package kr.mashup.branding.facade.application;

import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantNotFoundException;
import kr.mashup.branding.domain.application.ApplicationCreationRequestInvalidException;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormNotFoundException;
import kr.mashup.branding.domain.team.TeamNotFoundException;
import kr.mashup.branding.service.applicant.ApplicantService;
import kr.mashup.branding.service.application.ApplicationFormService;
import kr.mashup.branding.service.team.TeamService;
import kr.mashup.branding.ui.application.vo.ApplicationResponse;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.domain.application.ApplicationSubmitRequestVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import kr.mashup.branding.domain.application.confirmation.UpdateConfirmationVo;
import kr.mashup.branding.ui.application.vo.UpdateConfirmationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationFacadeService {
    private final TeamService teamService;
    private final ApplicantService applicantService;
    private final ApplicationService applicationService;
    private final ApplicationFormService applicationFormService;
    private final ApplicationAssembler applicationAssembler;


    /**
     * 각 팀의 지원서 상세페이지 접근시 빈 지원서 생성 또는 기존 지원서 조회
     */
    public ApplicationResponse create(Long applicantId, Long teamId) {

        final Applicant applicant;
        try {
            applicant = applicantService.getApplicant(applicantId);
        } catch (ApplicantNotFoundException e) {
            throw new ApplicationCreationRequestInvalidException("Applicant not found. applicantId: " + applicantId, e);
        }

        try {
            teamService.getTeam(teamId);
        } catch (TeamNotFoundException e) {
            throw new ApplicationCreationRequestInvalidException("Team not found. teamId: " + teamId, e);
        }

        final ApplicationForm applicationForm;
        try {
            applicationForm = applicationFormService.getApplicationFormsByTeamId(teamId)
                .stream()
                .findFirst()
                .orElseThrow(ApplicationFormNotFoundException::new);
        } catch (ApplicationFormNotFoundException e) {
            throw new ApplicationCreationRequestInvalidException(
                "ApplicationForm not found. teamId: " + teamId, e);
        }

        final Application application
            = applicationService.create(applicantId, applicant, applicationForm);

        return applicationAssembler.toApplicationResponse(application);
    }

    /**
     * 지원서 임시저장
     */
    public ApplicationResponse update(Long applicantId, Long applicationId, UpdateApplicationVo updateApplicationVo) {

        final Application application
            = applicationService.update(applicantId, applicationId, updateApplicationVo);

        return applicationAssembler.toApplicationResponse(application);
    }

    /**
     * 지원서 제출
     */
    public ApplicationResponse submit(
        Long applicantId,
        Long applicationId,
        ApplicationSubmitRequestVo applicationSubmitRequestVo
    ) {
        final Application application
            = applicationService.submit(applicantId, applicationId, applicationSubmitRequestVo);

        return applicationAssembler.toApplicationResponse(application);
    }

    /**
     * 내 지원서 목록 보기
     */
    public List<ApplicationResponse> getApplications(Long applicantId) {
        return applicationService.getApplications(applicantId)
            .stream()
            .map(applicationAssembler::toApplicationResponse).collect(Collectors.toList());
    }

    /**
     * 내 지원서 상세 보기
     */
    public ApplicationResponse getApplication(Long applicantId, Long applicationId) {

        final Application application
            = applicationService.getApplication(applicantId, applicationId);

        return applicationAssembler.toApplicationResponse(application);
    }

    /**
     * 인터뷰, 최종합격에 대한 지원자 응답
     */
    public ApplicationResponse updateConfirm(Long applicantId, Long applicationId,
                                             UpdateConfirmationRequest updateRequest) {
        final UpdateConfirmationVo updateConfirmationVo
            = UpdateConfirmationVo.of(applicationId, updateRequest.getStatus(), updateRequest.getRejectionReason());

        final Application application
            = applicationService.updateConfirmationFromApplicant(applicantId, updateConfirmationVo);

        return applicationAssembler.toApplicationResponse(application);
    }

    /**
     * (Test용) 인터뷰, 최종합격에 대한 지원자 응답
     */
    public ApplicationResponse updateConfirmForTest(Long applicantId, Long applicationId,
                                                    UpdateConfirmationRequest updateRequest) {

        final UpdateConfirmationVo updateConfirmationVo
            = UpdateConfirmationVo.of(applicationId, updateRequest.getStatus(), updateRequest.getRejectionReason());

        final Application application
            = applicationService.updateConfirmationForTest(applicantId,updateConfirmationVo);

        return applicationAssembler.toApplicationResponse(application);
    }
}
