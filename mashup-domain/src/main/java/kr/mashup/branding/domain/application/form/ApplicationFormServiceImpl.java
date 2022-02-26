package kr.mashup.branding.domain.application.form;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.application.ApplicationRepository;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationFormServiceImpl implements ApplicationFormService {
    private final TeamService teamService;
    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationFormScheduleValidator applicationFormScheduleValidator;

    @Override
    @Transactional
    public ApplicationForm create(Long adminMemberId, CreateApplicationFormVo createApplicationFormVo) {
        validateDate(adminMemberId);
        Team team = teamService.getTeam(createApplicationFormVo.getTeamId());
        if (applicationFormRepository.existsByTeam_teamId(team.getTeamId())) {
            throw new ApplicationFormAlreadyExistException("해당 팀에 다른 설문지가 이미 존재합니다. teamId: " + team.getTeamId());
        }
        if (applicationFormRepository.existsByNameLike(createApplicationFormVo.getName())) {
            throw new ApplicationFormNameDuplicatedException(
                "ApplicationForm 'name' is already used. name: " + createApplicationFormVo.getName());
        }

        ApplicationForm applicationForm = ApplicationForm.of(team, createApplicationFormVo.getName());
        List<Question> questions = createApplicationFormVo.getQuestionRequestVoList()
            .stream()
            .map(it -> Question.of(applicationForm, it))
            .collect(Collectors.toList());
        applicationForm.addQuestions(questions);
        return applicationFormRepository.save(applicationForm);
    }

    /**
     * 설문지를 생성, 수정 및 삭제할 수 있는지 검증
     */
    private void validateDate(Long adminMemberId) {
        try {
            applicationFormScheduleValidator.validate(LocalDateTime.now());
        } catch (ApplicationFormModificationNotAllowedException e) {
            log.info("Failed to modify applicationForm. adminMemberId: {}", adminMemberId);
            throw e;
        }
    }

    @Override
    @Transactional
    public ApplicationForm update(
        Long adminMemberId,
        Long applicationFormId,
        UpdateApplicationFormVo updateApplicationFormVo
    ) {
        validateDate(adminMemberId);
        ApplicationForm applicationForm = applicationFormRepository.findByApplicationFormId(applicationFormId)
            .orElseThrow(ApplicationFormNotFoundException::new);
        applicationForm.update(updateApplicationFormVo);
        if (applicationFormRepository.countByNameLike(applicationForm.getName()) > 1) {
            throw new ApplicationFormNameDuplicatedException(
                "ApplicationForm 'name' is already used. name: " + applicationForm.getName());
        }
        return applicationForm;
    }

    @Override
    @Transactional
    public void delete(Long adminMemberId, Long applicationFormId) {
        validateDate(adminMemberId);
        if (applicationRepository.existsByApplicationForm_ApplicationFormId(applicationFormId)) {
            throw new ApplicationFormDeleteFailedException();
        }

        applicationFormRepository.findByApplicationFormId(applicationFormId)
            .ifPresent(applicationFormRepository::delete);
    }

    @Override
    public ApplicationForm getApplicationFormById(Long applicationFormId) {
        return applicationFormRepository.findById(applicationFormId).map(
            it -> {
                log.info("ApplicationForm {}", it);
                return it;
            }
        )
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
