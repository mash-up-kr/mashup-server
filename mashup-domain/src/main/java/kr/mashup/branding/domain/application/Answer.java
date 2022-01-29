package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.application.form.Question;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    public static Answer empty(Question question) {
        Answer answer = new Answer();
        answer.question = question;
        answer.content = "";
        return answer;
    }
}
