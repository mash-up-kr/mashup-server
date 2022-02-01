package kr.mashup.branding.domain.application.form;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
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
    @Column(columnDefinition = "TEXT", length = 4000)
    private String content;

    /**
     * 권장하는 길이
     */
    private Integer properSize;

    /**
     * 필수 질문 여부
     */
    private Boolean required;

    /**
     * 질문 종류 (단답형, 장문형)
     */
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Question of(CreateQuestionVo createQuestionVo) {
        Question question = new Question();
        question.content = createQuestionVo.getContent();
        question.properSize = createQuestionVo.getProperSize();
        question.required = createQuestionVo.getRequired();
        question.questionType = createQuestionVo.getQuestionType();
        return question;
    }
}
