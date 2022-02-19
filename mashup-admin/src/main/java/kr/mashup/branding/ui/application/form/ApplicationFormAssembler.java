package kr.mashup.branding.ui.application.form;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.application.form.QuestionRequestVo;
import kr.mashup.branding.domain.application.form.UpdateApplicationFormVo;
import kr.mashup.branding.ui.team.TeamAssembler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationFormAssembler {
    private final TeamAssembler teamAssembler;

    ApplicationFormResponse toApplicationFormResponse(ApplicationForm applicationForm) {
        return new ApplicationFormResponse(
            applicationForm.getApplicationFormId(),
            applicationForm.getName(),
            teamAssembler.toTeamResponse(applicationForm.getTeam()),
            applicationForm.getQuestions()
                .stream()
                .map(this::toQuestionResponse)
                .collect(Collectors.toList()),
            applicationForm.getCreatedAt(),
            applicationForm.getCreatedBy(),
            applicationForm.getUpdatedAt(),
            applicationForm.getUpdatedBy()
        );
    }

    public QuestionResponse toQuestionResponse(Question question) {
        return new QuestionResponse(
            question.getQuestionId(),
            question.getContent(),
            question.getMaxContentLength(),
            question.getDescription(),
            question.getRequired(),
            question.getQuestionType()
        );
    }

    CreateApplicationFormVo toCreateApplicationFormVo(
        CreateApplicationFormRequest createApplicationFormRequest
    ) {
        return CreateApplicationFormVo.of(
            createApplicationFormRequest.getTeamId(),
            createApplicationFormRequest.getQuestions()
                .stream()
                .map(this::toQuestionRequestVo)
                .collect(Collectors.toList()),
            createApplicationFormRequest.getName()
        );
    }

    QuestionRequestVo toQuestionRequestVo(QuestionRequest questionRequest) {
        return QuestionRequestVo.of(
            questionRequest.getContent(),
            questionRequest.getMaxContentLength(),
            questionRequest.getDescription(),
            questionRequest.getRequired(),
            questionRequest.getQuestionType()
        );
    }

    UpdateApplicationFormVo toUpdateApplicationFormVo(UpdateApplicationFormRequest updateApplicationFormRequest) {
        return UpdateApplicationFormVo.of(
            updateApplicationFormRequest.getQuestions()
                .stream()
                .map(this::toQuestionRequestVo)
                .collect(Collectors.toList()),
            updateApplicationFormRequest.getName()
        );
    }
}
