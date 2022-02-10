package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.application.Answer;
import kr.mashup.branding.domain.application.AnswerRequestVo;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import kr.mashup.branding.domain.application.result.ApplicationResult;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleService;
import kr.mashup.branding.ui.applicant.ApplicantAssembler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationAssembler {
    private final ApplicantAssembler applicantAssembler;
    private final RecruitmentScheduleService recruitmentScheduleService;

    CreateApplicationVo toCreateApplicationVo(CreateApplicationRequest createApplicationRequest) {
        Assert.notNull(createApplicationRequest, "'createApplicationRequest' must not be null");
        return new CreateApplicationVo(createApplicationRequest.getTeamId());
    }

    ApplicationResponse toApplicationResponse(Application application) {
        Assert.notNull(application, "'application' must not be null");
        return new ApplicationResponse(
            application.getApplicationId(),
            applicantAssembler.toApplicationResponse(application.getApplicant()),
            application.getStatus().name(),
            application.getAnswers().stream()
                .map(this::toAnswerResponse)
                .collect(Collectors.toList()),
            toApplicationResultResponse(application.getApplicationResult()),
            application.getPrivacyPolicyAgreed()
        );
    }

    ApplicationResultResponse toApplicationResultResponse(ApplicationResult applicationResult) {
        return new ApplicationResultResponse(
            toApplicationStatusResponse(applicationResult),
            applicationResult.getInterviewStartedAt(),
            applicationResult.getInterviewEndedAt()
        );
    }

    private ApplicationStatusResponse toApplicationStatusResponse(ApplicationResult applicationResult) {
        // TODO: 13기 생기면 기수별로 일정 관리해야함
        LocalDateTime now = LocalDateTime.now();
        if (!recruitmentScheduleService.canAnnounceScreeningResult(now)) {
            return ApplicationStatusResponse.submitted(applicationResult.getApplication().getStatus());
        }
        if (!recruitmentScheduleService.canAnnounceInterviewResult(now)) {
            return ApplicationStatusResponse.screeningResult(applicationResult.getScreeningStatus());
        }
        return ApplicationStatusResponse.interviewResult(
            applicationResult.getScreeningStatus(),
            applicationResult.getInterviewStatus()
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
                .collect(Collectors.toList()),
            updateApplicationRequest.getPrivacyPolicyAgreed()
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
