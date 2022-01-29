package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

import kr.mashup.branding.domain.application.form.ApplicationForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// TODO: member
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

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
}
