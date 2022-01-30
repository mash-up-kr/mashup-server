package kr.mashup.branding.domain.application.form;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationFormServiceImpl implements ApplicationFormService {
    private final TeamService teamService;
    private final QuestionService questionService;
    private final ApplicationFormRepository applicationFormRepository;

    @Override
    @Transactional
    public ApplicationForm createApplicationForm(CreateApplicationFormVo createApplicationFormVo) {
        Team team = teamService.getTeam(createApplicationFormVo.getTeamId());
        ApplicationForm applicationForm = ApplicationForm.of(team,
            createApplicationFormVo.getCreateQuestionVoList()
                .stream()
                .map(questionService::createQuestion)
                .collect(Collectors.toList()));
        return applicationFormRepository.save(applicationForm);
    }

    @Override
    public ApplicationForm getApplicationFormById(Long applicationFormId) {
        return applicationFormRepository.findById(applicationFormId)
            .orElseThrow(ApplicationFormNotFoundException::new);
    }

    @Override
    public List<ApplicationForm> getApplicationFormsByTeamId(Long teamId) {
        return applicationFormRepository.findByTeam_teamId(teamId);
    }
}
