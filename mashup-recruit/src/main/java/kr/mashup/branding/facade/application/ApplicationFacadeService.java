package kr.mashup.branding.facade.application;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kr.mashup.branding.config.EmailConfig;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.exception.ApplicantNotFoundException;
import kr.mashup.branding.domain.application.ApplicationCreationRequestInvalidException;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormNotFoundException;
import kr.mashup.branding.domain.email.EmailTemplateName;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.service.applicant.ApplicantService;
import kr.mashup.branding.service.application.ApplicationFormService;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.recruitmentschedule.RecruitmentScheduleService;
import kr.mashup.branding.service.team.TeamService;
import kr.mashup.branding.ui.application.vo.ApplicationFormResponse;
import kr.mashup.branding.ui.application.vo.ApplicationResponse;
import kr.mashup.branding.ui.application.vo.RecruitScheduleResponse;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;
    private final ApplicationAssembler applicationAssembler;
    private final GenerationService generationService;
    private final RecruitmentScheduleService recruitmentScheduleService;

    /**
     * 지원서 목록 조회
     */
    public List<ApplicationFormResponse> getApplicationForms(final Integer generationNumber) {

        final Generation generation =
            generationService.getByNumberOrThrow(generationNumber);
        final List<Team> teams = teamService.findAllTeamsByGeneration(generation);

        return applicationFormService
            .getApplicationFormsByTeam(teams)
            .stream()
            .map(ApplicationFormResponse::of)
            .collect(Collectors.toList());


    }
    /**
     * 각 팀의 지원서 상세페이지 접근시 빈 지원서 생성 또는 기존 지원서 조회
     */
    public ApplicationResponse create(Long applicantId, Long teamId) {

        // 지원자 조회
        final Applicant applicant;
        try {
            applicant = applicantService.getApplicant(applicantId);
        } catch (ApplicantNotFoundException e) {
            throw new ApplicationCreationRequestInvalidException("Applicant not found. applicantId: " + applicantId, e);
        }

        // 존재하는 team id 인지 검증 -> team은 generation에 종속적이다.
        final boolean isExistTeam = teamService.isExistTeam(teamId);
        if(!isExistTeam){
            throw new ApplicationCreationRequestInvalidException("Team not found. teamId: " + teamId);
        }

        // 지원 form 조회
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
        final String email = application.getApplicant().getEmail();


        final Map<String, String> bindingData = new HashMap<>();
        bindingData.put("name", application.getApplicant().getName());
        bindingData.put("position", application.getApplicationForm().getTeam().getName());

        eventPublisher.publishEvent(
            EmailSendEvent.of(
                EmailConfig.RECRUIT_ADDRESS,
                email,
                EmailTemplateName.SUBMIT,
                bindingData));

        return applicationAssembler.toApplicationResponse(application);
    }

    /**
     * 내 지원서 목록 보기
     */
    public List<ApplicationResponse> getApplications(Long applicantId) {
        return applicationService
            .getApplication(applicantId)
            .stream()
            .map(applicationAssembler::toApplicationResponse)
            .collect(Collectors.toList());
    }

    /**
     * 내 지원서 상세 보기
     */
    public ApplicationResponse getApplication(Long applicantId, Long applicationId) {

        final Application application
            = applicationService.getApplications(applicantId, applicationId);

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

    public List<RecruitScheduleResponse> getRecruitSchedule(Integer generationNumber) {
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);

        return recruitmentScheduleService.getAll(generation)
            .stream()
            .map(RecruitScheduleResponse::of)
            .collect(Collectors.toList());
    }
}
