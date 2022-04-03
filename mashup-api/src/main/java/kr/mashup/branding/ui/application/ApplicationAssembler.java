package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.application.Answer;
import kr.mashup.branding.domain.application.AnswerRequestVo;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationSubmitRequestVo;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.application.result.ApplicationResult;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleService;
import kr.mashup.branding.ui.applicant.ApplicantAssembler;
import kr.mashup.branding.ui.team.TeamAssembler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationAssembler {
    private final ApplicantAssembler applicantAssembler;
    private final TeamAssembler teamAssembler;
    private final RecruitmentScheduleService recruitmentScheduleService;

    CreateApplicationVo toCreateApplicationVo(CreateApplicationRequest createApplicationRequest) {
        Assert.notNull(createApplicationRequest, "'createApplicationRequest' must not be null");
        return new CreateApplicationVo(createApplicationRequest.getTeamId());
    }

    ApplicationResponse toApplicationResponse(Application application) {
        Assert.notNull(application, "'application' must not be null");
        return new ApplicationResponse(
            application.getApplicationId(),
            applicantAssembler.toApplicantResponse(application.getApplicant()),
            teamAssembler.toTeamResponse(application.getApplicationForm().getTeam()),
            toApplicantConfirmationStatus(application.getConfirmation().getStatus()),
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

    UpdateApplicationVo toUpdateApplicationVo(UpdateApplicationRequest updateApplicationRequest) {
        Assert.notNull(updateApplicationRequest, "'updateApplicationRequest' must not be null");
        Assert.hasText(updateApplicationRequest.getApplicantName(), "'applicantName' must not be blank");
        Assert.hasText(updateApplicationRequest.getPhoneNumber(), "'phoneNumber' must not be blank");
        Assert.notNull(updateApplicationRequest.getBirthdate(), "'birthdate' must not be null");
        return UpdateApplicationVo.of(
            updateApplicationRequest.getApplicantName(),
            updateApplicationRequest.getPhoneNumber(),
            updateApplicationRequest.getBirthdate(),
            updateApplicationRequest.getDepartment(),
            updateApplicationRequest.getResidence(),
            Optional.ofNullable(updateApplicationRequest.getAnswers())
                .map(it -> it.stream()
                    .map(this::toAnswerRequestVo)
                    .collect(Collectors.toList())
                ).orElse(null),
            updateApplicationRequest.getPrivacyPolicyAgreed()
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

    ApplicationSubmitRequestVo toApplicationSubmitRequestVo(ApplicationSubmitRequest applicationSubmitRequest) {
        Assert.hasText(applicationSubmitRequest.getApplicantName(), "'applicantName' must not be blank");
        Assert.hasText(applicationSubmitRequest.getPhoneNumber(), "'phoneNumber' must not be blank");
        Assert.notNull(applicationSubmitRequest.getBirthdate(), "'birthdate' must not be null");
        Assert.hasText(applicationSubmitRequest.getResidence(), "'residence' must not be blank");
        Assert.hasText(applicationSubmitRequest.getDepartment(), "'department' must not be blank");
        return ApplicationSubmitRequestVo.of(
            applicationSubmitRequest.getApplicantName(),
            applicationSubmitRequest.getPhoneNumber(),
            applicationSubmitRequest.getBirthdate(),
            applicationSubmitRequest.getDepartment(),
            applicationSubmitRequest.getResidence(),
            Optional.ofNullable(applicationSubmitRequest.getAnswers())
                .map(it -> it.stream()
                    .map(this::toAnswerRequestVo)
                    .collect(Collectors.toList()))
                .orElse(null),
            applicationSubmitRequest.getPrivacyPolicyAgreed()
        );
    }

    private ZonedDateTime convertToZonedDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"));
    }
}
