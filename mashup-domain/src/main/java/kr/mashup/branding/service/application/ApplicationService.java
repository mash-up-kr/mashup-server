package kr.mashup.branding.service.application;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.*;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.confirmation.UpdateConfirmationVo;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.domain.exception.ForbiddenException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;
import kr.mashup.branding.repository.application.ApplicationRepository;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import kr.mashup.branding.service.recruitmentschedule.RecruitmentScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Service
@Transactional
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
    public Application create(Long applicantId, Applicant applicant, ApplicationForm applicationForm) {

        Assert.notNull(applicantId, "'applicantId' must not be null");

        final Generation generation = applicationForm.getTeam().getGeneration();
        validateDate(generation, applicantId);

        final List<Application> applications
            = applicationRepository.findByApplicantAndApplicationForm(applicant, applicationForm);

        if (!applications.isEmpty()) {
            final Application application = applications.get(0);
            if (application.isSubmitted()) {
                throw new ApplicationAlreadySubmittedException();
            }
            return application;
        }

        return applicationRepository.save(Application.of(applicant, applicationForm));
    }

    public Application update(Long applicantId, Long applicationId, UpdateApplicationVo updateApplicationVo) {
        Assert.notNull(applicationId, "'applicationId' must not be null");
        Assert.notNull(updateApplicationVo, "'updateApplicationVo' must not be null");

        final Application application = findByApplicantIdAndApplicationId(applicantId, applicationId);
        final Generation generation = application.getApplicationForm().getTeam().getGeneration();

        validateDate(generation, applicantId);
        validateSubmittedApplicationExists(generation, applicantId, applicationId);

        application.update(updateApplicationVo);

        return application;
    }


    public Application submit(
        Long applicantId,
        Long applicationId,
        ApplicationSubmitRequestVo applicationSubmitRequestVo
    ) {
        Assert.notNull(applicationId, "'applicationId' must not be null");

        final Application application = findByApplicantIdAndApplicationId(applicantId, applicationId);
        final Generation generation = application.getApplicationForm().getTeam().getGeneration();

        validateDate(generation, applicantId);
        validateSubmittedApplicationExists(generation, applicantId, applicationId);


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
    private void validateDate(Generation generation, Long applicantId) {
        try {
            applicationScheduleValidator.validate(generation, LocalDateTime.now());
        } catch (ApplicationModificationNotAllowedException e) {
            log.info("Failed to modify application. applicantId: {}", applicantId);
            throw e;
        }
    }

    /**
     * 이미 제출한 지원서가 존재하는지 검증
     *
     * @param generation    해당 기수
     * @param applicantId   지원자 식별자
     * @param applicationId 지원서 식별자
     */
    private void validateSubmittedApplicationExists(
        final Generation generation,
        final Long applicantId,
        final Long applicationId) {

        final boolean alreadySubmittedInGeneration =
            applicationRepository
                .existByGenerationAndApplicantAndApplicationStatus(
                    generation,
                    applicantId,
                    ApplicationStatus.SUBMITTED);

        if (alreadySubmittedInGeneration) {
            throw new ApplicationAlreadySubmittedException(
                "Failed to submit application. applicationId: " + applicationId);
        }
    }

    /**
     * 지원서 1개에 대해서 결과, 면접 일정을 변경한다.
     */
    public Application updateResult(
        AdminMember adminMember,
        UpdateApplicationResultVo updateApplicationResultVo
    ) {

        Assert.notNull(updateApplicationResultVo, "'updateApplicationResultVo' must not be null");

        final Long applicationId = updateApplicationResultVo.getApplicationId();
        Assert.notNull(applicationId, "'applicationId' must not be null");

        final Application application = findApplicationById(applicationId);

        checkAdminMemberAuthority(adminMember, application.getApplicationForm().getTeam().getName());

        application.updateResult(updateApplicationResultVo);

        return application;
    }

    public Application updateConfirmationFromApplicant(Long applicantId,
                                                       UpdateConfirmationVo updateConfirmationVo) {
        final Long applicationId = updateConfirmationVo.getApplicationId();
        final ApplicantConfirmationStatus updateStatus = updateConfirmationVo.getStatus();
        final String rejectReason = updateConfirmationVo.getRejectionReason();

        final Application application = findByApplicantIdAndApplicationId(applicantId, applicationId);

        application.updateConfirm(updateStatus, rejectReason);

        return application;
    }


    public Application updateConfirmationForTest(Long applicantId,
                                                 UpdateConfirmationVo updateConfirmationVo) {
        final Long applicationId = updateConfirmationVo.getApplicationId();

        final Application application = findByApplicantIdAndApplicationId(applicantId, applicationId);

        application.updateConfirmationStatus(updateConfirmationVo.getStatus());

        return application;
    }

    public List<Application> getApplications(List<Long> applicationIds) {

        return applicationRepository.findAllById(applicationIds);
    }

    public List<Application> getApplication(Long applicantId) {

        final List<Application> applications
            = applicationRepository
            .findByIdAndStatusIn(applicantId, ApplicationStatus.validSet());

        applications.forEach(this::updateApplicationResult);

        return applications;
    }


    public Map<Applicant, Application> getApplications(Generation generation, List<Applicant> applicants) {

        final Map<Applicant, Application> applicationMap
            = applicationRepository
            .findApplicationsByApplicantIn(generation, applicants) // 기수당 1지원 정책 가정, 재지원 필터링위해서 generation 사용
            .stream()
            .collect(Collectors.toMap(Application::getApplicant, it -> it, (applicant1, applicant2) -> applicant1));

        return applicationMap;
    }


    public List<Application> getApplicationsByStatusAndEventName(Generation generation, ApplicationStatus status,
                                                                 RecruitmentScheduleEventName eventName) {

        final LocalDateTime eventOccurredAt
            = recruitmentScheduleService.getByEventName(generation, eventName).getEventOccurredAt();

        return applicationRepository.findByStatusAndCreatedAtBefore(status, eventOccurredAt);
    }

    // TODO: 상세 조회시 form 도 같이 조합해서 내려주어야할듯 (teamId, memberId 요청하면 해당팀 쓰던 지원서 질문, 내용 다 합쳐서)
    public Application getApplications(Long applicantId, Long applicationId) {
        Assert.notNull(applicantId, "'applicantId' must not be null");
        Assert.notNull(applicationId, "'applicationId' must not be null");

        final Application application = findApplicationById(applicationId);

        updateApplicationResult(application);

        if (!applicantId.equals(application.getApplicant().getApplicantId())) {
            throw new ForbiddenException(ResultCode.APPLICATION_NO_ACCESS, "No Access application.");
        }

        return application;
    }

    public Application getApplicationFromAdmin(AdminMember adminMember, Long applicationId) {
        Assert.notNull(applicationId, "'applicationId' must not be null");

        final Application application = findApplicationById(applicationId);

        checkAdminMemberAuthority(adminMember, application.getApplicationForm().getTeam().getName());

        return application;
    }


    public Page<Application> getApplications(Long adminMemberId, Generation generation,
                                             ApplicationQueryVo applicationQueryVo) {

        return applicationRepository.findBy(generation, applicationQueryVo);
    }

    public void updateApplicationResult(Application application) {
        Generation generation = application.getApplicationForm().getTeam().getGeneration();
        // 서류 제출 기간이 지난 경우에도 지원서를 제출하지 않았다면(생성됨, 작성중의 상태) 서류 평가 대상이 아님을 업데이트 한다.
        if (!recruitmentScheduleService.isRecruitAvailable(generation, LocalDateTime.now()) && !application.getStatus().isSubmitted()
            && (application.getApplicationResult().getScreeningStatus() != ApplicationScreeningStatus.NOT_APPLICABLE)
        ) {
            application.getApplicationResult().updateScreeningStatus(ApplicationScreeningStatus.NOT_APPLICABLE);
        }
    }

    public void updateInterviewGuideLink(Long teamId, String link) {
        applicationRepository.findInterviewerByTeamId(teamId)
            .forEach(interviewer -> {
                interviewer.getApplicationResult().updateInterviewGuideLink(link);
            });
    }

    public void deleteByApplicationFormId(Long applicationFormId) {
        List<Application> applications = applicationRepository
            .findByApplicationForm(applicationFormId);

        List<Long> applicationIds = applications.stream()
            .map(Application::getApplicationId)
            .collect(Collectors.toList());

        applicationRepository.deleteAllById(applicationIds);
    }

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

    private Application findApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(ApplicationNotFoundException::new);
    }

    private Application findByApplicantIdAndApplicationId(Long applicantId, Long applicationId) {
        return applicationRepository
            .findByApplicationAndApplicant(applicationId, applicantId)
            .orElseThrow(ApplicationNotFoundException::new);
    }

}
