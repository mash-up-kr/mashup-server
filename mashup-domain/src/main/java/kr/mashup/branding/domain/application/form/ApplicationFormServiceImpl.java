package kr.mashup.branding.domain.application.form;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.schedule.RecruitmentScheduleService;
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
    private final RecruitmentScheduleService recruitmentScheduleService;

    @Override
    @Transactional
    public ApplicationForm create(CreateApplicationFormVo createApplicationFormVo) {
        Team team = teamService.getTeam(createApplicationFormVo.getTeamId());
        if (applicationFormRepository.existsByTeam_teamId(team.getTeamId())) {
            throw new ApplicationFormAlreadyExistException("해당 팀에 다른 설문지가 이미 존재합니다. teamId: " + team.getTeamId());
        }
        ApplicationForm applicationForm = ApplicationForm.of(team,
            createApplicationFormVo.getQuestionRequestVoList()
                .stream()
                .map(questionService::createQuestion)
                .collect(Collectors.toList()),
            createApplicationFormVo.getName());
        return applicationFormRepository.save(applicationForm);
    }

    @Override
    @Transactional
    public ApplicationForm update(
        Long applicationFormId,
        UpdateApplicationFormVo updateApplicationFormVo
    ) {
        if (recruitmentScheduleService.isRecruitStarted(LocalDateTime.now())) {
            throw new ApplicationFormModificationNotAllowedException("모집 시작시각 이후에는 지원서를 수정할 수 없습니다");
        }
        ApplicationForm applicationForm = applicationFormRepository.findByApplicationFormId(
            applicationFormId)
            .orElseThrow(ApplicationFormNotFoundException::new);
        applicationForm.update(updateApplicationFormVo);
        return applicationForm;
    }

    @Override
    @Transactional
    public void delete(Long applicationFormId) {
        if (recruitmentScheduleService.isRecruitStarted(LocalDateTime.now())) {
            throw new ApplicationFormModificationNotAllowedException("모집 시작시각 이후에는 지원서를 삭제할 수 없습니다");
        }
        applicationFormRepository.findByApplicationFormId(applicationFormId)
            .ifPresent(applicationFormRepository::delete);
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

    @Override
    public Page<ApplicationForm> getApplicationForms(ApplicationFormQueryVo applicationFormQueryVo) {
        return applicationFormRepository.findByApplicationFormQueryVo(applicationFormQueryVo);
    }

    @Override
    public Page<ApplicationForm> getApplicationForms(Long teamId, Pageable pageable) {
        return applicationFormRepository.findByTeam_teamId(teamId, pageable);
    }
}
