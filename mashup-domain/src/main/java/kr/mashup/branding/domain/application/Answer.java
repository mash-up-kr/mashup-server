package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.application.form.Question;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ToString
@EqualsAndHashCode(of = "answerId")
@EntityListeners(AuditingEntityListener.class)
public class Answer {
    @Id
    @GeneratedValue
    private Long answerId;

    private String content;

    @ManyToOne
    private Question question;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Answer of(Question question, String content) {
        Answer answer = new Answer();
        answer.question = question;
        answer.content = content;
        return answer;
    }
}
