package kr.mashup.branding.service.application;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationAlreadySubmittedException;
import kr.mashup.branding.domain.application.ApplicationCreationRequestInvalidException;
import kr.mashup.branding.domain.application.ApplicationModificationNotAllowedException;
import kr.mashup.branding.domain.application.ApplicationNotFoundException;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.ApplicationScheduleValidator;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.ApplicationSubmitRequestInvalidException;
import kr.mashup.branding.domain.application.ApplicationSubmitRequestVo;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import kr.mashup.branding.repository.application.ApplicationRepository;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import kr.mashup.branding.domain.exception.ForbiddenException;
import kr.mashup.branding.service.recruitmentschedule.RecruitmentScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantNotFoundException;
import kr.mashup.branding.service.applicant.ApplicantService;
import kr.mashup.branding.domain.application.confirmation.UpdateConfirmationVo;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormNotFoundException;
import kr.mashup.branding.service.application.form.ApplicationFormService;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.domain.team.TeamNotFoundException;
import kr.mashup.branding.service.team.TeamService;
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
    private final RecruitmentScheduleService recruitmentScheduleService;
    private final ApplicationScheduleValidator applicationScheduleValidator;

    /**
     * 1. ?????? ?????? ????????? ???????????? ?????? ??????: CREATED ????????? ????????? ??????
     * 2. ?????? ?????? ?????? ????????? ???????????? ?????? ??????:
     * - CREATED, WRITING: ??????
     * - SUBMITTED: ??????
     *
     * @param applicantId         ????????? ?????????
     * @param createApplicationVo ????????? ?????? ?????? ??????
     * @return ?????????
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
     * ????????? ??????, ??????, ?????? ????????? ???????????? ??????
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
     * ?????? ????????? ???????????? ??????????????? ??????
     *
     * @param applicantId   ????????? ?????????
     * @param applicationId ????????? ?????????
     */
    private void validateSubmittedApplicationExists(Long applicantId, Long applicationId) {
        if (applicationRepository.existsByApplicant_applicantIdAndStatus(applicantId, ApplicationStatus.SUBMITTED)) {
            throw new ApplicationAlreadySubmittedException(
                "Failed to submit application. applicationId: " + applicationId);
        }
    }

    /**
     * ????????? 1?????? ????????? ??????, ?????? ????????? ????????????.
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

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);

        checkAdminMemberAuthority(adminMemberId, application.getApplicationForm().getTeam().getName());

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
        application.updateConfirm(updateConfirmationVo.getStatus(), updateConfirmationVo.getRejectionReason());
        return application;
    }

    @Override
    @Transactional
    public Application updateConfirmationForTest(Long applicantId,
                                                 UpdateConfirmationVo updateConfirmationVo) {
        Application application = applicationRepository.findByApplicationIdAndApplicant_applicantId(
                updateConfirmationVo.getApplicationId(), applicantId)
            .orElseThrow(ApplicationNotFoundException::new);
        application.updateConfirmationStatus(updateConfirmationVo.getStatus());
        return application;
    }

    @Override
    @Transactional
    public List<Application> getApplications(Long applicantId) {
        List<Application> applications = applicationRepository.findByApplicant_applicantIdAndStatusIn(
            applicantId, ApplicationStatus.validSet());
        applications.forEach(this::updateApplicationResult);
        return applications;
    }

    @Override
    public List<Application> getApplicationsByApplicationStatusAndEventName(ApplicationStatus status, String eventName) {
        LocalDateTime eventOccurredAt = recruitmentScheduleService.getByEventName(eventName).getEventOccurredAt();
        return applicationRepository.findByStatusAndCreatedAtBefore(status, eventOccurredAt);
    }

    // TODO: ?????? ????????? form ??? ?????? ???????????? ????????????????????? (teamId, memberId ???????????? ????????? ?????? ????????? ??????, ?????? ??? ?????????)
    @Override
    @Transactional
    public Application getApplication(Long applicantId, Long applicationId) {
        Assert.notNull(applicantId, "'applicantId' must not be null");
        Assert.notNull(applicationId, "'applicationId' must not be null");

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
        updateApplicationResult(application);

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

        checkAdminMemberAuthority(adminMemberId, application.getApplicationForm().getTeam().getName());

        return application;
    }

    @Override
    public Page<Application> getApplications(Long adminMemberId, ApplicationQueryVo applicationQueryVo) {
        return applicationRepository.findBy(applicationQueryVo);
    }

    @Override
    public void updateApplicationResult(Application application) {
        // ?????? ?????? ????????? ?????? ???????????? ???????????? ???????????? ????????????(?????????, ???????????? ??????) ?????? ?????? ????????? ????????? ???????????? ??????.
        if (!recruitmentScheduleService.isRecruitAvailable(LocalDateTime.now()) && !application.getStatus().isSubmitted()
            && (application.getApplicationResult().getScreeningStatus() != ApplicationScreeningStatus.NOT_APPLICABLE)
        ) {
            application.getApplicationResult().updateScreeningStatus(ApplicationScreeningStatus.NOT_APPLICABLE);
        }
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

    private void checkAdminMemberAuthority(Long adminMemberId, String teamName) {
        AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        if (Arrays.stream(adminMember.getPosition().getAuthorities())
            .noneMatch(team -> team.getName().equals(teamName))) {
            throw new ForbiddenException(ResultCode.ADMIN_MEMBER_NO_ACCESS_TEAM, "No Access to other team applications.");
        }
    }

    private void checkHelperAdminMember(Long adminMemberId) {
        AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        if (adminMember.getPosition().name().contains("HELPER")) {
            throw new ForbiddenException(ResultCode.ADMIN_MEMBER_NO_UPDATE_PERMISSION, "Helper is not authorized to update.");
        }
    }
}
