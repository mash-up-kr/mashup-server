package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.Answer;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationResult;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.facade.application.ApplicationDetailVo;
import kr.mashup.branding.ui.applicant.ApplicantAssembler;
import kr.mashup.branding.ui.application.form.ApplicationFormAssembler;
import kr.mashup.branding.ui.notification.sms.SmsRequestAssembler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationAssembler {
    private final ApplicantAssembler applicantAssembler;
    private final ApplicationFormAssembler applicationFormAssembler;
    private final SmsRequestAssembler smsRequestAssembler;

    ApplicationSimpleResponse toApplicationSimpleResponse(Application application) {
        return new ApplicationSimpleResponse(
            application.getApplicationId(),
            applicantAssembler.toApplicantResponse(application.getApplicant()),
            application.getConfirmation().getStatus(),
            application.getCreatedAt(),
            application.getUpdatedAt()
        );
    }

    ApplicationDetailResponse toApplicationDetailResponse(ApplicationDetailVo applicationDetailVo) {
        Application application = applicationDetailVo.getApplication();
        return new ApplicationDetailResponse(
            application.getApplicationId(),
            applicantAssembler.toApplicantResponse(application.getApplicant()),
            application.getApplicationForm().getQuestions()
                .stream()
                .map(applicationFormAssembler::toQuestionResponse)
                .collect(Collectors.toList()),
            application.getAnswers()
                .stream()
                .map(this::toAnswerResponse)
                .collect(Collectors.toList()),
            application.getConfirmation().getStatus(),
            toApplicationResultResponse(application.getApplicationResult()),
            application.getSubmittedAt(),
            application.getCreatedAt(),
            application.getUpdatedAt(),
            applicationDetailVo.getSmsRequests()
                .stream()
                .map(it -> smsRequestAssembler.toSmsRequestDetailResponse(it,
                    application.getApplicationForm().getTeam()))
                .collect(Collectors.toList())
        );
    }

    public List<UpdateApplicationResultVo> toUpdateApplicationResultsVoList(UpdateApplicationResultsRequest request) {
        ApplicationResultStatus applicationResultStatus = request.getApplicationResultStatus();
        return request.getApplicationIds()
            .stream()
            .map(it -> toUpdateApplicationResultVo(it, applicationResultStatus, null, null))
            .collect(Collectors.toList());
    }

    private UpdateApplicationResultVo toUpdateApplicationResultVo(
        Long applicationId,
        ApplicationResultStatus applicationResultStatus,
        LocalDateTime interviewStartedAt,
        LocalDateTime interviewEndedAt
    ) {
        switch (applicationResultStatus) {
            case NOT_RATED:
                return UpdateApplicationResultVo.notRated(applicationId);
            case SCREENING_FAILED:
                return UpdateApplicationResultVo.screeningFailed(applicationId);
            case SCREENING_TO_BE_DETERMINED:
                return UpdateApplicationResultVo.screeningToBeDetermined(applicationId);
            case SCREENING_PASSED:
                return UpdateApplicationResultVo.screeningPassed(
                    applicationId,
                    interviewStartedAt,
                    interviewEndedAt
                );
            case INTERVIEW_FAILED:
                return UpdateApplicationResultVo.interviewFailed(applicationId);
            case INTERVIEW_TO_BE_DETERMINED:
                return UpdateApplicationResultVo.interviewToBeDetermined(
                    applicationId,
                    interviewStartedAt,
                    interviewEndedAt
                );
            case INTERVIEW_PASSED:
                return UpdateApplicationResultVo.interviewPassed(applicationId);
        }
        throw new IllegalStateException();
    }

    UpdateApplicationResultVo toUpdateApplicationResultVo(
        Long applicationId,
        UpdateApplicationResultRequest request
    ) {
        return toUpdateApplicationResultVo(
            applicationId,
            request.getApplicationResultStatus(),
            request.getInterviewStartedAt(),
            request.getInterviewEndedAt()
        );
    }

    ApplicationQueryVo toApplicationQueryVo(
        String searchWord,
        Long teamId,
        ApplicantConfirmationStatus confirmStatus,
        ApplicationResultStatus resultStatus,
        Pageable pageable
    ) {
        return ApplicationQueryVo.of(
            searchWord,
            teamId,
            confirmStatus,
            toApplicationScreeningStatus(resultStatus),
            toApplicationInterviewStatus(resultStatus),
            pageable
        );
    }

    private ApplicationScreeningStatus toApplicationScreeningStatus(ApplicationResultStatus resultStatus) {
        if (resultStatus == null) {
            return null;
        }
        switch (resultStatus) {
            case NOT_RATED:
                return ApplicationScreeningStatus.NOT_RATED;
            case SCREENING_FAILED:
                return ApplicationScreeningStatus.FAILED;
            case SCREENING_TO_BE_DETERMINED:
                return ApplicationScreeningStatus.TO_BE_DETERMINED;
            case SCREENING_PASSED:
                return ApplicationScreeningStatus.PASSED;
            case INTERVIEW_FAILED:
            case INTERVIEW_TO_BE_DETERMINED:
            case INTERVIEW_PASSED:
                return null;
        }
        throw new IllegalStateException();
    }

    private ApplicationInterviewStatus toApplicationInterviewStatus(ApplicationResultStatus resultStatus) {
        if (resultStatus == null) {
            return null;
        }
        switch (resultStatus) {
            case NOT_RATED:
            case SCREENING_FAILED:
            case SCREENING_TO_BE_DETERMINED:
            case SCREENING_PASSED:
                return null;
            case INTERVIEW_FAILED:
                return ApplicationInterviewStatus.FAILED;
            case INTERVIEW_TO_BE_DETERMINED:
                return ApplicationInterviewStatus.TO_BE_DETERMINED;
            case INTERVIEW_PASSED:
                return ApplicationInterviewStatus.PASSED;
        }
        throw new IllegalStateException();
    }

    private AnswerResponse toAnswerResponse(Answer answer) {
        return new AnswerResponse(
            answer.getAnswerId(),
            answer.getQuestion().getQuestionId(),
            answer.getContent()
        );
    }

    private ApplicationResultResponse toApplicationResultResponse(ApplicationResult applicationResult) {
        return new ApplicationResultResponse(
            ApplicationResultStatus.of(
                applicationResult.getScreeningStatus(),
                applicationResult.getInterviewStatus()
            ),
            applicationResult.getInterviewStartedAt(),
            applicationResult.getInterviewEndedAt()
        );
    }
}
