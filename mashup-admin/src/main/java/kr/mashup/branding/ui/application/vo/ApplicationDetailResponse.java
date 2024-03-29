package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.confirmation.Confirmation;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.email.EmailRequest;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.ui.applicant.ApplicantResponse;
import kr.mashup.branding.ui.application.form.vo.QuestionResponse;
import kr.mashup.branding.ui.team.vo.TeamResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ApplicationDetailResponse {
    private Long applicationId;
    private ApplicantResponse applicant;
    private TeamResponse team;
    private List<QuestionResponse> questions;
    private List<AnswerResponse> answers;
    private ApplicantConfirmationStatus confirmationStatus;
    private String rejectionReason;
    private ApplicationResultResponse result;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ApplicationEmailResponse> applicationEmailResponses;

    public static ApplicationDetailResponse of(
        Application application,
        List<EmailRequest> emailRequests){

        final Confirmation confirmation = application.getConfirmation();
        final Applicant applicant = application.getApplicant();
        final ApplicationForm applicationForm = application.getApplicationForm();
        final Team team = applicationForm.getTeam();
        final List<Question> questions = applicationForm.getQuestions();

        final List<ApplicationEmailResponse> emailRequestResponses = emailRequests
            .stream()
            .sorted(Comparator.comparing(EmailRequest::getCreatedAt).reversed())
            .map(ApplicationEmailResponse::of)
            .collect(Collectors.toList());

        final List<QuestionResponse> questionResponses = questions
            .stream()
            .map(QuestionResponse::from)
            .collect(Collectors.toList());

        final List<AnswerResponse> answerResponses = application.getAnswers()
            .stream()
            .map(AnswerResponse::from)
            .collect(Collectors.toList());

        return new ApplicationDetailResponse(
            application.getApplicationId(), // 지원서 아이디
            ApplicantResponse.from(applicant), // 지원자 정보
            TeamResponse.from(team), // 팀 정보
            questionResponses, // 질문 목록
            answerResponses, // 응답 목록
            confirmation.getStatus(), // 확인여부
            confirmation.getRejectionReason(), // 거절 사유
            ApplicationResultResponse.from(application.getApplicationResult()), // 지원 결과
            application.getSubmittedAt(), // 제출 일시
            application.getCreatedAt(), // 생성 일시
            application.getUpdatedAt(), // 수정 일시
            emailRequestResponses // 이메일 발송 내역
        );
    }
}
