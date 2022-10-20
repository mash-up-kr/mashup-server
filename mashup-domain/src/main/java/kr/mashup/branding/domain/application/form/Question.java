package kr.mashup.branding.domain.application.form;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@ToString(of = {"questionId", "maxContentLength", "description", "required", "questionType", "createdBy", "createdAt",
    "updatedBy", "updatedAt"})
@EqualsAndHashCode(of = "questionId")
@EntityListeners(AuditingEntityListener.class)
public class Question {
    @Id
    @GeneratedValue
    private Long questionId;

    /**
     * 내용
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 최대 길이 (n글자 이하)
     */
    private Integer maxContentLength;

    /**
     * 질문 설명
     */
    private String description;

    /**
     * 필수 질문 여부
     */
    private Boolean required;

    /**
     * 질문 종류 (단답형, 장문형)
     */
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_form_id")
    private ApplicationForm applicationForm;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Question of(ApplicationForm applicationForm, QuestionRequestVo questionRequestVo) {
        Question question = new Question();
        question.applicationForm = applicationForm;
        question.content = questionRequestVo.getContent();
        question.description = questionRequestVo.getDescription();
        question.required = Optional.ofNullable(questionRequestVo.getRequired()).orElse(false);
        if (questionRequestVo.getQuestionType() == null ||
            questionRequestVo.getQuestionType() == QuestionType.SINGLE_LINE_TEXT) {
            question.questionType = QuestionType.SINGLE_LINE_TEXT;
            question.maxContentLength = null;
        } else {
            question.questionType = questionRequestVo.getQuestionType();
            question.maxContentLength = questionRequestVo.getMaxContentLength();
        }
        return question;
    }

    @PrePersist
    @PreUpdate
    public void updateApplicationFormModifyInfo() {
        applicationForm.setModifiedInfo(updatedBy, updatedAt);
    }
}
