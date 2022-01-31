package kr.mashup.branding.ui.application;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.application.AnswerRequestVo;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;

@Component
public class ApplicationAssembler {
    CreateApplicationVo toCreateApplicationVo(CreateApplicationRequest createApplicationRequest) {
        Assert.notNull(createApplicationRequest, "'createApplicationRequest' must not be null");
        return new CreateApplicationVo(createApplicationRequest.getTeamId());
    }

    ApplicationResponse toApplicationResponse(Application application) {
        Assert.notNull(application, "'application' must not be null");
        return new ApplicationResponse(application.getApplicationId());
    }

    UpdateApplicationVo toUpdateApplicationVo(UpdateApplicationRequest updateApplicationRequest) {
        Assert.notNull(updateApplicationRequest, "'updateApplicationRequest' must not be null");
        return UpdateApplicationVo.of(
            updateApplicationRequest.getName(),
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
            answerRequest.getQuestionId(),
            answerRequest.getContent()
        );
    }
}
