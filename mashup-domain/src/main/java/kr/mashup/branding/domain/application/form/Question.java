package kr.mashup.branding.domain.application.form;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Question of(CreateQuestionVo createQuestionVo) {
        Question question = new Question();
        question.content = createQuestionVo.getContent();
        question.properSize = createQuestionVo.getProperSize();
        return question;
    }
}
