package kr.mashup.branding.ui.application.form;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.CreateQuestionVo;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.application.form.QuestionType;

@Component
public class ApplicationFormAssembler {
    ApplicationFormResponse toApplicationFormResponse(ApplicationForm applicationForm) {
        return new ApplicationFormResponse(
            applicationForm.getApplicationFormId(),
            applicationForm.getName(),
            applicationForm.getQuestions()
                .stream()
                .map(this::toQuestionResponse)
                .collect(Collectors.toList())
        );
    }

    QuestionResponse toQuestionResponse(Question question) {
        return new QuestionResponse(
            question.getQuestionId(),
            question.getContent(),
            question.getProperSize(),
            question.getRequired()
        );
    }

    CreateApplicationFormVo toCreateApplicationFormVo(
        Long teamId,
        CreateApplicationFormRequest createApplicationFormRequest
    ) {
        return CreateApplicationFormVo.of(
            teamId,
            createApplicationFormRequest.getQuestions()
                .stream()
                .map(this::toCreateQuestionVo)
                .collect(Collectors.toList()),
            createApplicationFormRequest.getName()
        );
    }

    CreateQuestionVo toCreateQuestionVo(CreateQuestionRequest createQuestionRequest) {
        return CreateQuestionVo.of(
            createQuestionRequest.getContent(),
            createQuestionRequest.getProperSize(),
            createQuestionRequest.getRequired(),
            QuestionType.valueOf(createQuestionRequest.getQuestionType())
        );
    }
}
