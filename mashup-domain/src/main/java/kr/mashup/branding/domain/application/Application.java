package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.confirmation.Confirmation;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.application.result.ApplicationResult;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(of = {"applicationId", "status", "submittedAt", "privacyPolicyAgreed", "createdAt", "updatedAt"})
@EqualsAndHashCode(of = "applicationId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Application {
    @Id
    @GeneratedValue
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "application_form_id")
    private ApplicationForm applicationForm;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "application")
    private ApplicationResult applicationResult;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "application")
    private Confirmation confirmation;

    /**
     * 지원자가 지원서 작성중인 상태
     */
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    private final List<Answer> answers = new ArrayList<>();

    /**
     * 개인정보 처리방침 동의여부
     */
    private Boolean privacyPolicyAgreed;

    /**
     * 제출 완료 시각
     */
    private LocalDateTime submittedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * 빈 지원서 생성
     */
    static Application of(
        Applicant applicant,
        ApplicationForm applicationForm
    ) {
        Application application = new Application();
        application.applicant = applicant;
        application.applicationForm = applicationForm;
        application.applicationResult = ApplicationResult.of(application);
        application.confirmation = Confirmation.of(application);
        application.status = ApplicationStatus.CREATED;
        List<Answer> answers = applicationForm.getQuestions()
            .stream()
            .map(Answer::empty)
            .collect(Collectors.toList());
        application.answers.addAll(answers);
        return application;
    }

    /**
     * 지원서 임시 저장
     */
    void update(UpdateApplicationVo updateApplicationVo) {
        Assert.notNull(updateApplicationVo, "'updateApplicationVo' must not be null");

        applicant.update(
            updateApplicationVo.getName(),
            updateApplicationVo.getPhoneNumber(),
            updateApplicationVo.getBirthdate(),
            updateApplicationVo.getDepartment(),
            updateApplicationVo.getResidence()
        );
        if (updateApplicationVo.getAnswerRequestVoList() != null) {
            updateAnswers(updateApplicationVo.getAnswerRequestVoList());
        }
        status = status.update();
        privacyPolicyAgreed = updateApplicationVo.getPrivacyPolicyAgreed();
    }

    private void updateAnswers(List<AnswerRequestVo> answerRequestVos) {
        Map<Long, AnswerRequestVo> questionAnswerMap = answerRequestVos
            .stream()
            .collect(Collectors.toMap(AnswerRequestVo::getAnswerId, Function.identity()));
        answers.forEach(it -> {
            Long answerId = it.getAnswerId();
            AnswerRequestVo answerRequestVo = questionAnswerMap.get(answerId);
            it.update(answerRequestVo.getContent());
        });
    }

    /**
     * 지원서 제출
     */
    void submit(ApplicationSubmitRequestVo applicationSubmitRequestVo) {
        applicant.submit(
            applicationSubmitRequestVo.getApplicantName(),
            applicationSubmitRequestVo.getPhoneNumber(),
            applicationSubmitRequestVo.getBirthdate(),
            applicationSubmitRequestVo.getDepartment(),
            applicationSubmitRequestVo.getResidence()
        );

        validateAnswerRequests(applicationSubmitRequestVo.getAnswerRequestVoList());
        updateAnswers(applicationSubmitRequestVo.getAnswerRequestVoList());

        validatePrivacyPolicyAgreed(applicationSubmitRequestVo.getPrivacyPolicyAgreed());
        this.privacyPolicyAgreed = applicationSubmitRequestVo.getPrivacyPolicyAgreed();

        status = status.submit();

        if (status.isSubmitted()) {
            submittedAt = LocalDateTime.now();
        }
    }

    private void validateAnswerRequests(List<AnswerRequestVo> answerRequestVos) {
        if (CollectionUtils.isEmpty(answerRequestVos)) {
            throw new IllegalArgumentException("'answers' must not be null or empty");
        }
        Assert.notNull(applicationForm.getQuestions(),
            "'applicationForm.questions' must not be null. applicationFormId: "
                + applicationForm.getApplicationFormId());
        Assert.isTrue(
            applicationForm.getQuestions().size() == answerRequestVos.size(),
            "Size of 'answers' must be equal to size of 'questions'"
        );
        Map<Long, Question> questionMap = applicationForm.getQuestions()
            .stream()
            .collect(Collectors.toMap(
                Question::getQuestionId,
                Function.identity()
            ));
        for (AnswerRequestVo answerRequestVo : answerRequestVos) {
            Question question = questionMap.get(answerRequestVo.getQuestionId());
            // 질문을 찾을 수 없는 경우
            if (question == null) {
                throw new IllegalArgumentException(
                    "Question not found. questionId: " + answerRequestVo.getQuestionId() + ", answerId: "
                        + answerRequestVo.getAnswerId());
            }
            // 필수 질문인데 응답이 비어있는 경우
            if (Boolean.TRUE == question.getRequired()) {
                if (!StringUtils.hasLength(answerRequestVo.getContent())) {
                    throw new IllegalArgumentException(
                        "Answer's content must not be null or empty because it's question is required");
                }
            }
            // 질문 최대 글자수를 초과하는 경우
            if (question.getMaxContentLength() != null && answerRequestVo.getContent() != null) {
                if (question.getMaxContentLength() < answerRequestVo.getContent().length()) {
                    throw new IllegalArgumentException("Answer's content must be less then or equal to " +
                        question.getMaxContentLength());
                }
            }
        }
    }

    private void validatePrivacyPolicyAgreed(Boolean privacyPolicyAgreed) {
        if (Boolean.TRUE != privacyPolicyAgreed) {
            throw new PrivacyPolicyNotAgreedException("'privacyPolicyAgreed' must be true. privacyPolicyAgreed: "
                + privacyPolicyAgreed);
        }
    }

    /**
     * 지원서 결과 및 면접시간 수정
     */
    void updateResult(UpdateApplicationResultVo updateApplicationResultVo) {
        applicationResult.updateResult(updateApplicationResultVo);
        confirmation.updateFromAdmin(updateApplicationResultVo.getScreeningStatus(), updateApplicationResultVo.getInterviewStatus());
    }

    void updateConfirm(ApplicantConfirmationStatus status) {
        confirmation.updateFromApplicant(status);
    }

    public void updateConfirmationStatus(ApplicantConfirmationStatus status) {
        confirmation.updateApplicantConfirmationStatus(status);
    }

    boolean isSubmitted() {
        return status.isSubmitted();
    }
}
