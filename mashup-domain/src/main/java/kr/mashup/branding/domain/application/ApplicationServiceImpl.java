package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantNotFoundException;
import kr.mashup.branding.domain.applicant.ApplicantService;
import kr.mashup.branding.domain.MashupSchedule;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormNotFoundException;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
import kr.mashup.branding.domain.application.progress.ApplicationProgressService;
import kr.mashup.branding.domain.application.progress.UpdateApplicationProgressVo;
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
    private final ApplicationProgressService applicationProgressService;
    private final TeamService teamService;
    private final ApplicantService applicantService;

    // get or create
    // TODO: 모르겠고 teamId 줄테니 다내놔! 에 대해서 고민해보기
    @Override
    @Transactional
    public Application create(Long applicantId, CreateApplicationVo createApplicationVo) {
        Assert.notNull(applicantId, "'applicantId' must not be null");
        Assert.notNull(createApplicationVo, "'createApplicationVo' must not be null");

        validateDate(LocalDateTime.now());

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

        validateDate(LocalDateTime.now());

        // TODO: applicant 이름, 연락처 저장
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
        application.update(updateApplicationVo);
        return application;
    }

    @Override
    @Transactional
    public Application submit(Long applicationId) {
        Assert.notNull(applicationId, "'applicationId' must not be null");

        validateDate(LocalDateTime.now());

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
        application.submit();
        return application;
    }

    /**
     * 지원서 생성, 수정, 제출 가능한 시각인지 검증
     */
    private void validateDate(LocalDateTime localDateTime) {
        if (!MashupSchedule.isRecruitAvailable(localDateTime)) {
            throw new IllegalArgumentException("지원서 제출 기간이 아닙니다. ");
        }
    }

    @Override
    @Transactional
    public Application updateResult(UpdateApplicationResultVo updateApplicationResultVo) {
        Application application = applicationRepository.findById(updateApplicationResultVo.getApplicationId())
            .orElseThrow(ApplicationNotFoundException::new);
        application.updateResult(updateApplicationResultVo.getStatus());
        return application;
    }

    @Override
    @Transactional
    public Application updateProgressFromApplicant(UpdateApplicationProgressVo updateApplicationProgressVo) {
        Application application = applicationRepository.findById(updateApplicationProgressVo.getApplicationId())
            .orElseThrow(ApplicationNotFoundException::new);
        application.updateProgressFromApplicant(updateApplicationProgressVo.getStatus());
        return application;
    }

    @Override
    public List<Application> getApplications(Long applicantId) {
        return applicationRepository.findByApplicant_applicantIdAndStatusIn(applicantId, ApplicationStatus.validSet());
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
    public Page<Application> getApplications(String searchWord, Pageable pageable) {
        return applicationRepository.findAll(pageable);
    }
}
