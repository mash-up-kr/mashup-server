package kr.mashup.branding.domain.application.form;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public Question createQuestion(QuestionRequestVo questionRequestVo) {
        return questionRepository.save(Question.of(questionRequestVo));
    }
}
