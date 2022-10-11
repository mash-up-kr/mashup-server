package kr.mashup.branding.service.application;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
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
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
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
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.domain.team.TeamNotFoundException;
import kr.mashup.branding.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final AdminMemberService adminMemberService;
    private final RecruitmentScheduleService recruitmentScheduleService;
    private final ApplicationScheduleValidator applicationScheduleValidator;

    /**
     * 1. 해당 팀에 생성한 지원서가 없는 경우: CREATED 상태의 지원서 생성
     * 2. 해당 팀에 이미 생성한 지원서가 있는 경우:
     * - CREATED, WRITING: 성공
     * - SUBMITTED: 실패
     *
     * @return 지원서
     */
    @Transactional
    public Application create(Long applicantId, Applicant applicant, ApplicationForm applicationForm) {
        Assert.notNull(applicantId, "'applicantId' must not be null");

        validateDate(applicantId);

        List<Application> applications = applicationRepository.findByApplicantAndApplicationForm(applicant, applicationForm);

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
    @Transactional
    public Application updateResult(
        AdminMember adminMember,
        UpdateApplicationResultVo updateApplicationResultVo
    ) {

        Assert.notNull(updateApplicationResultVo, "'updateApplicationResultVo' must not be null");

        Long applicationId = updateApplicationResultVo.getApplicationId();
        Assert.notNull(applicationId, "'applicationId' must not be null");

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);

        checkAdminMemberAuthority(adminMember, application.getApplicationForm().getTeam().getName());

        application.updateResult(updateApplicationResultVo);
        return application;
    }

    @Transactional
    public Application updateConfirmationFromApplicant(Long applicantId,
                                                       UpdateConfirmationVo updateConfirmationVo) {
        final Long applicationId = updateConfirmationVo.getApplicationId();

        Application application = applicationRepository.findByApplicationIdAndApplicant_applicantId(
                applicationId, applicantId)
            .orElseThrow(ApplicationNotFoundException::new);

        final ApplicantConfirmationStatus updateStatus = updateConfirmationVo.getStatus();
        final String rejectReason = updateConfirmationVo.getRejectionReason();

        application.updateConfirm(updateStatus, rejectReason);

        return application;
    }

    @Transactional
    public Application updateConfirmationForTest(Long applicantId,
                                                 UpdateConfirmationVo updateConfirmationVo) {
        final Long applicationId = updateConfirmationVo.getApplicationId();

        Application application = applicationRepository
            .findByApplicationIdAndApplicant_applicantId(applicationId, applicantId)
            .orElseThrow(ApplicationNotFoundException::new);

        application.updateConfirmationStatus(updateConfirmationVo.getStatus());

        return application;
    }

    @Transactional
    public List<Application> getApplications(Long applicantId) {
        List<Application> applications = applicationRepository.findByApplicant_applicantIdAndStatusIn(
            applicantId, ApplicationStatus.validSet());
        applications.forEach(this::updateApplicationResult);
        return applications;
    }

    public List<Application> getApplicationsByApplicationStatusAndEventName(ApplicationStatus status, String eventName) {
        LocalDateTime eventOccurredAt = recruitmentScheduleService.getByEventName(eventName).getEventOccurredAt();
        return applicationRepository.findByStatusAndCreatedAtBefore(status, eventOccurredAt);
    }

    // TODO: 상세 조회시 form 도 같이 조합해서 내려주어야할듯 (teamId, memberId 요청하면 해당팀 쓰던 지원서 질문, 내용 다 합쳐서)
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

    public Application getApplicationFromAdmin(AdminMember adminMember, Long applicationId) {
        Assert.notNull(applicationId, "'applicationId' must not be null");

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);

        checkAdminMemberAuthority(adminMember, application.getApplicationForm().getTeam().getName());

        return application;
    }

    public Page<Application> getApplications(Long adminMemberId, ApplicationQueryVo applicationQueryVo) {
        return applicationRepository.findBy(applicationQueryVo);
    }

    public void updateApplicationResult(Application application) {
        // 서류 제출 기간이 지난 경우에도 지원서를 제출하지 않았다면(생성됨, 작성중의 상태) 서류 평가 대상이 아님을 업데이트 한다.
        if (!recruitmentScheduleService.isRecruitAvailable(LocalDateTime.now()) && !application.getStatus().isSubmitted()
            && (application.getApplicationResult().getScreeningStatus() != ApplicationScreeningStatus.NOT_APPLICABLE)
        ) {
            application.getApplicationResult().updateScreeningStatus(ApplicationScreeningStatus.NOT_APPLICABLE);
        }
    }

    @Transactional
    public void deleteByApplicationFormId(Long applicationFormId) {
        List<Application> applications = applicationRepository
            .findByApplicationForm_ApplicationFormId(applicationFormId);

        List<Long> applicationIds = applications.stream()
            .map(Application::getApplicationId)
            .collect(Collectors.toList());

        applicationRepository.deleteAllById(applicationIds);
    }

    @Transactional
    public void delete(Long applicationId) {
        applicationRepository.findById(applicationId)
            .ifPresent(applicationRepository::delete);
    }

    private void checkAdminMemberAuthority(AdminMember adminMember, String teamName) {

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
