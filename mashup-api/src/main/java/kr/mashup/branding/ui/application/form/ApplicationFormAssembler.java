package kr.mashup.branding.ui.application.form;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.ui.team.TeamAssembler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationFormAssembler {
    private final TeamAssembler teamAssembler;

    ApplicationFormResponse toApplicationFormResponse(ApplicationForm applicationForm) {
        return new ApplicationFormResponse(
            applicationForm.getApplicationFormId(),
            teamAssembler.toTeamResponse(applicationForm.getTeam()),
            applicationForm.getQuestions().stream()
                .map(this::toQuestionResponse)
                .collect(Collectors.toList()),
            applicationForm.getName()
        );
    }

    private QuestionResponse toQuestionResponse(Question question) {
        return new QuestionResponse(
            question.getQuestionId(),
            question.getContent(),
            question.getProperSize(),
            question.getRequired(),
            question.getQuestionType().name()
        );
    }
}
