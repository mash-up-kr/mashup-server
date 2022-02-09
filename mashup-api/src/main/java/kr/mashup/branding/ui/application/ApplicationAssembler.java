package kr.mashup.branding.ui.application;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.application.Answer;
import kr.mashup.branding.domain.application.AnswerRequestVo;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import kr.mashup.branding.ui.applicant.ApplicantAssembler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationAssembler {
    private final ApplicantAssembler applicantAssembler;

    CreateApplicationVo toCreateApplicationVo(CreateApplicationRequest createApplicationRequest) {
        Assert.notNull(createApplicationRequest, "'createApplicationRequest' must not be null");
        return new CreateApplicationVo(createApplicationRequest.getTeamId());
    }

    ApplicationResponse toApplicationResponse(Application application) {
        Assert.notNull(application, "'application' must not be null");

        return new ApplicationResponse(
            application.getApplicationId(),
            applicantAssembler.toApplicationResponse(application.getApplicant()),
            application.getConfirmation().getStatus(),
            application.getStatus().name(),
            application.getAnswers().stream()
                .map(this::toAnswerResponse)
                .collect(Collectors.toList())
        );
    }

    UpdateApplicationVo toUpdateApplicationVo(UpdateApplicationRequest updateApplicationRequest) {
        Assert.notNull(updateApplicationRequest, "'updateApplicationRequest' must not be null");
        return UpdateApplicationVo.of(
            updateApplicationRequest.getApplicantName(),
            updateApplicationRequest.getPhoneNumber(),
            updateApplicationRequest.getAnswers()
                .stream()
                .map(this::toAnswerRequestVo)
                .collect(Collectors.toList())
        );
    }

    private AnswerRequestVo toAnswerRequestVo(AnswerRequest answerRequest) {
        Assert.notNull(answerRequest, "'answerRequest' must not be null");
        return AnswerRequestVo.of(
            answerRequest.getAnswerId(),
            answerRequest.getContent()
        );
    }

    private AnswerResponse toAnswerResponse(Answer answer) {
        return new AnswerResponse(
            answer.getAnswerId(),
            answer.getContent()
        );
    }
}
