package kr.mashup.branding.facade.application;

import kr.mashup.branding.domain.application.Answer;
import kr.mashup.branding.domain.application.AnswerRequestVo;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.application.result.ApplicationResult;
import kr.mashup.branding.service.recruitmentschedule.RecruitmentScheduleService;
import kr.mashup.branding.ui.applicant.vo.ApplicantResponse;
import kr.mashup.branding.ui.application.vo.AnswerRequest;
import kr.mashup.branding.ui.application.vo.AnswerResponse;
import kr.mashup.branding.ui.application.vo.ApplicationResponse;
import kr.mashup.branding.ui.application.vo.ApplicationResultResponse;
import kr.mashup.branding.ui.application.vo.ApplicationStatusResponse;
import kr.mashup.branding.ui.application.vo.QuestionResponse;
import kr.mashup.branding.ui.team.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApplicationAssembler {
    private final RecruitmentScheduleService recruitmentScheduleService;

    ApplicationResponse toApplicationResponse(Application application) {
        Assert.notNull(application, "'application' must not be null");
        return new ApplicationResponse(
            application.getApplicationId(),
            ApplicantResponse.from(application.getApplicant()),
            TeamResponse.from(application.getApplicationForm().getTeam()),
            toApplicantConfirmationStatus(application.getConfirmation().getStatus()),
            application.getConfirmation().getRejectionReason(),
            application.getStatus(),
            application.getSubmittedAt(),
            application.getApplicationForm().getQuestions()
                .stream()
                .map(this::toQuestionResponse)
                .collect(Collectors.toList()),
            application.getAnswers()
                .stream()
                .map(this::toAnswerResponse)
                .collect(Collectors.toList()),
            toApplicationResultResponse(application.getApplicationResult()),
            application.getPrivacyPolicyAgreed()
        );
    }

    ApplicantConfirmationStatus toApplicantConfirmationStatus(ApplicantConfirmationStatus applicantConfirmationStatus) {
        LocalDateTime now = LocalDateTime.now();
        if (!recruitmentScheduleService.canAnnounceScreeningResult(now)) {
            return ApplicantConfirmationStatus.TO_BE_DETERMINED;
        }
        if (!recruitmentScheduleService.canAnnounceInterviewResult(now) &&
            applicantConfirmationStatus == ApplicantConfirmationStatus.FINAL_CONFIRM_WAITING) {
            return ApplicantConfirmationStatus.INTERVIEW_CONFIRM_ACCEPTED;
        }
        return applicantConfirmationStatus;
    }

    ApplicationResultResponse toApplicationResultResponse(ApplicationResult applicationResult) {
        LocalDateTime interviewStartedAt = applicationResult.getInterviewStartedAt();
        LocalDateTime interviewEndedAt = applicationResult.getInterviewEndedAt();
        String interviewGuideLink = applicationResult.getInterviewGuideLink();

        if (!recruitmentScheduleService.canAnnounceScreeningResult(LocalDateTime.now())) {
            interviewStartedAt = null;
            interviewEndedAt = null;
            interviewGuideLink = null;
        }
        return new ApplicationResultResponse(
            toApplicationStatusResponse(applicationResult),
            convertToZonedDateTime(interviewStartedAt),
            convertToZonedDateTime(interviewEndedAt),
            interviewGuideLink
        );
    }

    private ApplicationStatusResponse toApplicationStatusResponse(ApplicationResult applicationResult) {
        // TODO: 13기 생기면 기수별로 일정 관리해야함
        LocalDateTime now = LocalDateTime.now();

        if (recruitmentScheduleService.isRecruitAvailable(now)) { // 서류 마감 전
            return ApplicationStatusResponse.submitted(applicationResult.getApplication().getStatus());
        }
        if (!recruitmentScheduleService.canAnnounceScreeningResult(now)) { // 서류 마감 후 ~ 서류 발표 전
            return ApplicationStatusResponse.beforeResult(applicationResult.getApplication().getStatus());
        }
        if (!recruitmentScheduleService.canAnnounceInterviewResult(now)) { // 서류 발표 후 ~ 면접 발표 전
            return ApplicationStatusResponse.screeningResult(
                applicationResult.getApplication().getStatus(),
                applicationResult.getScreeningStatus()
            );
        }
        return ApplicationStatusResponse.interviewResult( // 면접 발표 후
            applicationResult.getScreeningStatus(),
            applicationResult.getInterviewStatus(),
            applicationResult.getApplication().getConfirmation().getStatus()
        );
    }


    private AnswerRequestVo toAnswerRequestVo(AnswerRequest answerRequest) {
        Assert.notNull(answerRequest, "'answerRequest' must not be null");
        return AnswerRequestVo.of(
            answerRequest.getAnswerId(),
            answerRequest.getQuestionId(),
            answerRequest.getContent()
        );
    }

    private AnswerResponse toAnswerResponse(Answer answer) {
        return new AnswerResponse(
            answer.getAnswerId(),
            answer.getQuestion().getQuestionId(),
            answer.getContent()
        );
    }

    private QuestionResponse toQuestionResponse(Question question) {
        return new QuestionResponse(
            question.getQuestionId(),
            question.getContent(),
            question.getMaxContentLength(),
            question.getDescription(),
            question.getRequired(),
            question.getQuestionType()
        );
    }


    private ZonedDateTime convertToZonedDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"));
    }
}
