package kr.mashup.branding.domain.application.form;

import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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
    public ApplicationForm getApplicationFormByTeamId(Long teamId) {
        return applicationFormRepository.findByTeam_teamId(teamId)
                .orElseThrow(ApplicationFormNotFoundException::new);
    }
}
