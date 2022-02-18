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

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.confirmation.Confirmation;
import kr.mashup.branding.domain.application.form.ApplicationForm;
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
    private Applicant applicant;

    @ManyToOne
    private ApplicationForm applicationForm;

    @OneToOne(cascade = CascadeType.ALL)
    private ApplicationResult applicationResult;

    @OneToOne(cascade = CascadeType.ALL)
    private Confirmation confirmation;

    /**
     * 지원자가 지원서 작성중인 상태
     */
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "applicationId")
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
        application.confirmation = Confirmation.toBeDetermined();
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
        if (Boolean.TRUE != updateApplicationVo.getPrivacyPolicyAgreed()) {
            throw new PrivacyPolicyNotAgreedException("'privacyPolicyAgreed' must be true. privacyPolicyAgreed: "
                + updateApplicationVo.getPrivacyPolicyAgreed());
        }
        Map<Long, AnswerRequestVo> questionAnswerMap = updateApplicationVo.getAnswerRequestVoList()
            .stream()
            .collect(Collectors.toMap(AnswerRequestVo::getAnswerId, Function.identity()));
        answers.forEach(it -> {
            Long answerId = it.getAnswerId();
            AnswerRequestVo answerRequestVo = questionAnswerMap.get(answerId);
            it.update(answerRequestVo.getContent());
        });
        status = status.update();
        privacyPolicyAgreed = true;
    }

    /**
     * 지원서 제출
     */
    void submit() {
        if (Boolean.TRUE != this.privacyPolicyAgreed) {
            throw new PrivacyPolicyNotAgreedException("'privacyPolicyAgreed' must be true. privacyPolicyAgreed: "
                + this.privacyPolicyAgreed);
        }
        try {
            status = status.submit();
            submittedAt = LocalDateTime.now();
        } catch (ApplicationAlreadySubmittedException e) {
            // 이미 제출한 지원서를 다시 제출 시도하는 경우 성공으로 응답
        }
    }

    /**
     * 지원서 결과 및 면접시간 수정
     */
    void updateResult(UpdateApplicationResultVo updateApplicationResultVo) {
        applicationResult.updateResult(updateApplicationResultVo);
    }

    void updateConfirm(ApplicantConfirmationStatus status) {
        confirmation.updateFromApplicant(status);
    }

    boolean isSubmitted() {
        return status.isSubmitted();
    }
}
