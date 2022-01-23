package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
import kr.mashup.branding.domain.application.form.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationFormService applicationFormService;

    @Override
    @Transactional
    public Application createApplication(CreateApplicationVo createApplicationVo) {
        // TODO: member
//        val member = memberService.getMember(memberId =  createApplicationVo.memberId)
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(
                createApplicationVo.getApplicationFormId()
        );
        Map<Long, Question> questionMap = applicationForm.getQuestions()
                .stream()
                .collect(Collectors.toMap(Question::getQuestionId, Function.identity()));
        Application application = Application.of(
                applicationForm,
                createApplicationVo.getCreateAnswerVoList()
                        .stream()
                        .map(it -> Answer.of(questionMap.get(it.getQuestionId()), it.getContent()))
                        .collect(Collectors.toList())
        );
        return applicationRepository.save(application);
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
