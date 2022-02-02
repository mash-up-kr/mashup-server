package kr.mashup.branding.domain.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormNotFoundException;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
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

    // get or create
    // TODO: 모르겠고 teamId 줄테니 다내놔! 에 대해서 고민해보기
    @Override
    @Transactional
    public Application create(CreateApplicationVo createApplicationVo) {
        Assert.notNull(createApplicationVo, "'createApplicationVo' must not be null");

        try {
            teamService.getTeam(createApplicationVo.getTeamId());
        } catch (TeamNotFoundException e) {
            // TODO: 적절한 예외 만들기
            throw new IllegalArgumentException("Team not found. teamId: " + createApplicationVo.getTeamId());
        }
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

        // TODO: applicant 쿼리 조건에 applicant 추가
        List<Application> applications = applicationRepository.findByApplicationForm(applicationForm);
        // TODO: unique index (applicantId, applicationFormId)
        if (!applications.isEmpty()) {
            Application application = applications.get(0);
            if (application.isSubmitted()) {
                throw new ApplicationAlreadySubmittedException();
            }
            return application;
        }

        // TODO: applicant 지원서 생성시 applicant 추가
        return applicationRepository.save(Application.from(applicationForm));
    }

    @Override
    @Transactional
    public Application update(Long applicationId, UpdateApplicationVo updateApplicationVo) {
        Assert.notNull(applicationId, "'applicationId' must not be null");
        Assert.notNull(updateApplicationVo, "'updateApplicationVo' must not be null");

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

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
        application.submit();
        return application;
    }

    @Override
    public List<Application> getApplications(Long applicantId) {
        // TODO: applicant
        return applicationRepository.findByStatusIn(ApplicationStatus.validSet());
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
        return applicationRepository.findById(applicationId)
            .orElseThrow(ApplicationNotFoundException::new);
    }

    @Override
    public Page<Application> getApplications(String searchWord, Pageable pageable) {
        return applicationRepository.findAll(pageable);
    }
}
