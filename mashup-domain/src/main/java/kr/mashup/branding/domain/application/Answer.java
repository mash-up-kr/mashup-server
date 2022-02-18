package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.application.form.Question;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(of = {"answerId", "createdAt", "updatedAt"})
@EqualsAndHashCode(of = "answerId")
@EntityListeners(AuditingEntityListener.class)
public class Answer {
    @Id
    @GeneratedValue
    private Long answerId;

    @Column(columnDefinition = "TEXT")
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

    void update(String content) {
        Assert.notNull(content, "'content' must not be null");
        // TODO : validation (length, ..)
        this.content = content;
    }
}
