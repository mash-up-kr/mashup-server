package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberService;
import kr.mashup.branding.domain.exception.ForbiddenException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantNotFoundException;
import kr.mashup.branding.domain.applicant.ApplicantService;
import kr.mashup.branding.domain.application.confirmation.UpdateConfirmationVo;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormNotFoundException;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.domain.team.TeamNotFoundException;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationFormService applicationFormService;
    private final TeamService teamService;
    private final ApplicantService applicantService;
    private final AdminMemberService adminMemberService;
    private final ApplicationScheduleValidator applicationScheduleValidator;

    /**
     * 1. 해당 팀에 생성한 지원서가 없는 경우: CREATED 상태의 지원서 생성
     * 2. 해당 팀에 이미 생성한 지원서가 있는 경우:
     * - CREATED, WRITING: 성공
     * - SUBMITTED: 실패
     *
     * @param applicantId         지원자 식별자
     * @param createApplicationVo 지원서 생성 요청 정보
     * @return 지원서
     */
    @Override
    @Transactional
    public Application create(Long applicantId, CreateApplicationVo createApplicationVo) {
        Assert.notNull(applicantId, "'applicantId' must not be null");
        Assert.notNull(createApplicationVo, "'createApplicationVo' must not be null");

        validateDate(applicantId);

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

        List<Application> applications = applicationRepository.findByApplicantAndApplicationForm(applicant,
            applicationForm);
        // TODO: unique index (applicantId, applicationFormId)
        if (!applications.isEmpty()) {
            Application application = applications.get(0);
            if (application.isSubmitted()) {
                throw new ApplicationAlreadySubmittedException();
            }
            return application;
        }

        return applicationRepository.save(Application.of(applicant, applicationForm));
    }

    @Override
    @Transactional
    public Application update(Long applicantId, Long applicationId, UpdateApplicationVo updateApplicationVo) {
        Assert.notNull(applicationId, "'applicationId' must not be null");
        Assert.notNull(updateApplicationVo, "'updateApplicationVo' must not be null");

        validateDate(applicantId);
        validateSubmittedApplicationExists(applicantId, applicationId);

        Application application = applicationRepository.findByApplicationIdAndApplicant_applicantId(applicationId,
            applicantId).orElseThrow(ApplicationNotFoundException::new);

        application.update(updateApplicationVo);
        return application;
    }

    @Override
    @Transactional
    public Application submit(
        Long applicantId,
        Long applicationId,
        ApplicationSubmitRequestVo applicationSubmitRequestVo
    ) {
        Assert.notNull(applicationId, "'applicationId' must not be null");

        validateDate(applicantId);
        validateSubmittedApplicationExists(applicantId, applicationId);

        Application application = applicationRepository.findByApplicationIdAndApplicant_applicantId(applicationId,
            applicantId).orElseThrow(ApplicationNotFoundException::new);
        try {
            application.submit(applicationSubmitRequestVo);
        } catch (IllegalArgumentException e) {
            throw new ApplicationSubmitRequestInvalidException(
                "Failed to submit application. applicationId: " + applicationId, e);
        }
        return application;
    }

    /**
     * 지원서 생성, 수정, 제출 가능한 시각인지 검증
     */
    private void validateDate(Long applicantId) {
        try {
            applicationScheduleValidator.validate(LocalDateTime.now());
        } catch (ApplicationModificationNotAllowedException e) {
            log.info("Failed to modify application. applicantId: {}", applicantId);
            throw e;
        }
    }

    /**
     * 이미 제출한 지원서가 존재하는지 검증
     *
     * @param applicantId   지원자 식별자
     * @param applicationId 지원서 식별자
     */
    private void validateSubmittedApplicationExists(Long applicantId, Long applicationId) {
        if (applicationRepository.existsByApplicant_applicantIdAndStatus(applicantId, ApplicationStatus.SUBMITTED)) {
            throw new ApplicationAlreadySubmittedException(
                "Failed to submit application. applicationId: " + applicationId);
        }
    }

    /**
     * 지원서 1개에 대해서 결과, 면접 일정을 변경한다.
     */
    @Override
    @Transactional
    public Application updateResult(
        Long adminMemberId,
        UpdateApplicationResultVo updateApplicationResultVo
    ) {
        Assert.notNull(adminMemberId, "'adminMemberId' must not be null");
        Assert.notNull(updateApplicationResultVo, "'updateApplicationResultVo' must not be null");

        Long applicationId = updateApplicationResultVo.getApplicationId();
        Assert.notNull(applicationId, "'applicationId' must not be null");

        // TODO: adminMemberId 조회 및 권한 검증
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
        application.updateResult(updateApplicationResultVo);
        return application;
    }

    @Override
    @Transactional
    public Application updateConfirmationFromApplicant(Long applicantId,
                                                       UpdateConfirmationVo updateConfirmationVo) {
        Application application = applicationRepository.findByApplicationIdAndApplicant_applicantId(
                updateConfirmationVo.getApplicationId(), applicantId)
            .orElseThrow(ApplicationNotFoundException::new);
        application.updateConfirm(updateConfirmationVo.getStatus());
        return application;
    }

    @Override
    public List<Application> getApplications(Long applicantId) {
        return applicationRepository.findByApplicant_applicantIdAndStatusIn(applicantId,
            ApplicationStatus.validSet());
    }

    // TODO: 상세 조회시 form 도 같이 조합해서 내려주어야할듯 (teamId, memberId 요청하면 해당팀 쓰던 지원서 질문, 내용 다 합쳐서)
    @Override
    public Application getApplication(Long applicantId, Long applicationId) {
        Assert.notNull(applicantId, "'applicantId' must not be null");
        Assert.notNull(applicationId, "'applicationId' must not be null");

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);

        if (!applicantId.equals(application.getApplicant().getApplicantId())) {
            throw new ForbiddenException(ResultCode.APPLICATION_NO_ACCESS, "No Access application.");
        }
        return application;
    }

    @Override
    public Application getApplicationFromAdmin(Long adminMemberId, Long applicationId) {
        Assert.notNull(applicationId, "'applicationId' must not be null");

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
        String teamName = application.getApplicationForm().getTeam().getName();

        AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        if (Arrays.stream(adminMember.getPosition().getAuthorities())
            .noneMatch(team -> team.getName().equals(teamName))) {
            throw new ForbiddenException(ResultCode.ADMIN_MEMBER_NO_ACCESS_TEAM, "No Access to other team applications.");
        }
        return application;
    }

    @Override
    public Page<Application> getApplications(Long adminMemberId, ApplicationQueryVo applicationQueryVo) {
        return applicationRepository.findBy(applicationQueryVo);
    }

    @Override
    @Transactional
    public void deleteByApplicationFormId(Long applicationFormId) {
        List<Application> applications = applicationRepository
            .findByApplicationForm_ApplicationFormId(applicationFormId);

        List<Long> applicationIds = applications.stream()
            .map(Application::getApplicationId)
            .collect(Collectors.toList());

        applicationRepository.deleteAllById(applicationIds);
    }

    @Override
    @Transactional
    public void delete(Long applicationId) {
        applicationRepository.findById(applicationId)
            .ifPresent(applicationRepository::delete);
    }
}
