package kr.mashup.branding.service.application;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormAlreadyExistException;
import kr.mashup.branding.domain.application.form.ApplicationFormDeleteFailedException;
import kr.mashup.branding.domain.application.form.ApplicationFormModificationNotAllowedException;
import kr.mashup.branding.domain.application.form.ApplicationFormNameDuplicatedException;
import kr.mashup.branding.domain.application.form.ApplicationFormNotFoundException;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.domain.application.form.ApplicationFormScheduleValidator;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.application.form.UpdateApplicationFormVo;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.repository.application.ApplicationRepository;
import kr.mashup.branding.repository.application.form.ApplicationFormRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationFormService {
    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationFormScheduleValidator applicationFormScheduleValidator;

    @Transactional
    public ApplicationForm create(
        final Long adminMemberId,
        final Team team,
        final CreateApplicationFormVo createApplicationFormVo) {

        final Generation generation = team.getGeneration();
        validateRecruitingSchedule(generation, adminMemberId); // 모집시간 전에만 지원서를 수정할 수 있다.

        if (applicationFormRepository.existsByTeam_teamId(team.getTeamId())) {
            throw new ApplicationFormAlreadyExistException("해당 팀에 다른 설문지가 이미 존재합니다. teamId: " + team.getTeamId());
        }

        if (applicationFormRepository.existsByNameLike(createApplicationFormVo.getName())) {
            throw new ApplicationFormNameDuplicatedException(
                "ApplicationForm 'name' is already used. name: " + createApplicationFormVo.getName());
        }

        final ApplicationForm applicationForm = ApplicationForm.of(team, createApplicationFormVo.getName());

        final List<Question> questions
            = createApplicationFormVo
            .getQuestionRequestVoList()
            .stream()
            .map(it -> Question.of(applicationForm, it))
            .collect(Collectors.toList());

        applicationForm.addQuestions(questions);

        return applicationFormRepository.save(applicationForm);
    }

    /**
     * 설문지를 생성, 수정 및 삭제할 수 있는지 검증
     */
    private void validateRecruitingSchedule(Generation generation,Long adminMemberId) {
        try {
            applicationFormScheduleValidator.validate(generation, LocalDateTime.now());
        } catch (ApplicationFormModificationNotAllowedException e) {
            log.info("Failed to modify applicationForm. adminMemberId: {}", adminMemberId);
            throw e;
        }
    }

    @Transactional
    public ApplicationForm update(
        Long adminMemberId,
        Long applicationFormId,
        UpdateApplicationFormVo updateApplicationFormVo
    ) {


        final ApplicationForm applicationForm
            = applicationFormRepository
            .findByApplicationForm(applicationFormId)
            .orElseThrow(ApplicationFormNotFoundException::new);

        final Generation generation = applicationForm.getTeam().getGeneration();
        validateRecruitingSchedule(generation,adminMemberId);

        applicationForm.update(updateApplicationFormVo);

        if (applicationFormRepository.countByNameLike(applicationForm.getName()) > 1) {
            throw new ApplicationFormNameDuplicatedException(
                "ApplicationForm 'name' is already used. name: " + applicationForm.getName());
        }

        return applicationForm;
    }

    @Transactional
    public void delete(Long adminMemberId, Long applicationFormId) {


        if (applicationRepository.existByApplicationForm(applicationFormId)) {
            throw new ApplicationFormDeleteFailedException();
        }

        final ApplicationForm applicationForm = applicationFormRepository
            .findByApplicationForm(applicationFormId).orElseThrow(ApplicationFormNotFoundException::new);
        final Generation generation = applicationForm.getTeam().getGeneration();

        validateRecruitingSchedule(generation, adminMemberId);

        applicationFormRepository.delete(applicationForm);
    }

    public ApplicationForm getApplicationFormById(Long applicationFormId) {
        return applicationFormRepository
            .findById(applicationFormId)
            .map(
                it -> {
                    log.info("ApplicationForm {}", it);
                    return it;
                }
            )
            .orElseThrow(ApplicationFormNotFoundException::new);
    }

    public List<ApplicationForm> getApplicationFormsByTeamId(Long teamId) {
        return applicationFormRepository.findByTeam(teamId);
    }

    public Page<ApplicationForm> getApplicationForms(Generation generation, ApplicationFormQueryVo applicationFormQueryVo) {
        return applicationFormRepository.findByApplicationFormQueryVo(generation, applicationFormQueryVo);
    }
}
