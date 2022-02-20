package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private final ApplicationScheduleValidator applicationScheduleValidator;

    // get or create
    @Override
    @Transactional
    public Application create(Long applicantId, CreateApplicationVo createApplicationVo) {
        Assert.notNull(applicantId, "'applicantId' must not be null");
        Assert.notNull(createApplicationVo, "'createApplicationVo' must not be null");

        validateDate();

        final Applicant applicant;
        try {
            applicant = applicantService.getApplicant(applicantId);
        } catch (ApplicantNotFoundException e) {
            // TODO: 적절한 예외 만들기
            throw new IllegalArgumentException("Applicant not found. applicantId: " + applicantId);
        }

        try {
            teamService.getTeam(createApplicationVo.getTeamId());
        } catch (TeamNotFoundException e) {
            // TODO: 적절한 예외 만들기
            throw new IllegalArgumentException("Team not found. teamId: " + createApplicationVo.getTeamId());
        }

        // TODO: 이미 다른팀에 임시저장/제출완료한 다른 지원서가 있는지 검사

        final ApplicationForm applicationForm;
        try {
            applicationForm = applicationFormService.getApplicationFormsByTeamId(createApplicationVo.getTeamId())
                .stream()
                .findFirst()
                .orElseThrow(ApplicationFormNotFoundException::new);
        } catch (ApplicationFormNotFoundException e) {
            // TODO: 적절한 예외 만들기
            throw new IllegalArgumentException("ApplicationForm not found. teamId: " + createApplicationVo.getTeamId());
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
    public Application update(Long applicationId, UpdateApplicationVo updateApplicationVo) {
        Assert.notNull(applicationId, "'applicationId' must not be null");
        Assert.notNull(updateApplicationVo, "'updateApplicationVo' must not be null");

        validateDate();

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
        application.update(updateApplicationVo);
        application.getApplicant().update(
            updateApplicationVo.getName(),
            updateApplicationVo.getPhoneNumber()
        );
        return application;
    }

    @Override
    @Transactional
    public Application submit(Long applicationId) {
        Assert.notNull(applicationId, "'applicationId' must not be null");

        validateDate();
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
        application.submit();
        return application;
    }

    /**
     * 지원서 생성, 수정, 제출 가능한 시각인지 검증
     */
    private void validateDate() {
        applicationScheduleValidator.validate(LocalDateTime.now());
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
        // TODO: applicant
        return applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
    }

    @Override
    public Application getApplication(Long applicationId) {
        Assert.notNull(applicationId, "'applicationId' must not be null");
        // TODO: staff 권한 검사 (같은 팀만 볼수있어야함, 회장, 부회장, 브랜딩팀은 모든 팀 볼수있음)
        return applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
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
}
