package kr.mashup.branding.domain.application.form;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public Question createQuestion(CreateQuestionVo createQuestionVo) {
        return questionRepository.save(Question.of(createQuestionVo));
    }
}
