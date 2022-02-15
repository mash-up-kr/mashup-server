package kr.mashup.branding.domain.application.form;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
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
    private Integer maxContentSize;

    /**
     * 필수 질문 여부
     */
    private Boolean required;

    /**
     * 질문 종류 (단답형, 장문형)
     */
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Question of(QuestionRequestVo questionRequestVo) {
        Question question = new Question();
        question.content = questionRequestVo.getContent();
        question.maxContentSize = questionRequestVo.getMaxContentSize();
        question.required = questionRequestVo.getRequired();
        question.questionType = questionRequestVo.getQuestionType();
        return question;
    }
}
