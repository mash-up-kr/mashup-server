package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormNotFoundException;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
import kr.mashup.branding.domain.team.TeamNotFoundException;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationFormService applicationFormService;
    private final TeamService teamService;

    @Override
    @Transactional
    public Application createApplication(CreateApplicationVo createApplicationVo) {
        Assert.notNull(createApplicationVo, "'createApplicationVo' must not be null");
        // TODO: member
//        val member = memberService.getMember(memberId =  createApplicationVo.memberId)
        try {
            teamService.getTeam(createApplicationVo.getTeamId());
        } catch (TeamNotFoundException e) {
            // TODO: 적절한 예외 만들기
            throw new IllegalArgumentException("Team not found. teamId: " + createApplicationVo.getTeamId());
        }
        final ApplicationForm applicationForm;
        try {
            applicationForm = applicationFormService.getApplicationFormByTeamId(createApplicationVo.getTeamId());
        } catch (ApplicationFormNotFoundException e) {
            // TODO: 적절한 예외 만들기
            throw new IllegalArgumentException("ApplicationForm not found. teamId: " + createApplicationVo.getTeamId());
        }
        final Application application = Application.from(applicationForm);
        return applicationRepository.save(application);
    }

    @Override
    @Transactional
    public Application updateApplication(UpdateApplicationVo updateApplicationVo) {
        Assert.notNull(updateApplicationVo, "'updateApplicationVo' must not be null");
        Application application = applicationRepository.findById(updateApplicationVo.getApplicationId())
                .orElseThrow(ApplicationNotFoundException::new);
        return null;
    }

    @Override
    public List<Application> getAllApplication() {
        return applicationRepository.findAll();
    }

    @Override
    public List<Application> getApplications(Long memberId) {
        // TODO: memberId
        return applicationRepository.findAll();
    }

    @Override
    public Application getApplication(Long memberId, Long applicationId) {
        // TODO: memberId
        return applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);
    }

    @Override
    public Application getApplication(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);
    }
}
