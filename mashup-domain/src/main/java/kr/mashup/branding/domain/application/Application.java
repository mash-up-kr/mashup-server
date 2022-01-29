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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// TODO: applicant
//  지원자 relation 추가
@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "applicationId")
@EntityListeners(AuditingEntityListener.class)
public class Application {
    @Id
    @GeneratedValue
    private Long applicationId;

    @ManyToOne
    private ApplicationForm applicationForm;

    /**
     * 지원자가 지원서 작성중인 상태
     */
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "applicationId")
    private final List<Answer> answers = new ArrayList<>();

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
    static Application from(
        ApplicationForm applicationForm
    ) {
        Application application = new Application();
        application.applicationForm = applicationForm;
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
        Map<Long, AnswerRequestVo> questionAnswerMap = updateApplicationVo.getAnswerRequestVoList()
            .stream()
            .collect(Collectors.toMap(AnswerRequestVo::getQuestionId, Function.identity()));
        answers.forEach(it -> {
            Long questionId = it.getQuestion().getQuestionId();
            AnswerRequestVo answerRequestVo = questionAnswerMap.get(questionId);
            it.update(answerRequestVo.getContent());
        });
        status = status.update();
    }

    void submit() {
        try {
            status = status.submit();
            submittedAt = LocalDateTime.now();
        } catch (ApplicationAlreadySubmittedException e) {
            // 이미 제출한 지원서를 다시 제출 시도하는 경우 성공으로 응답
        }
    }
}
