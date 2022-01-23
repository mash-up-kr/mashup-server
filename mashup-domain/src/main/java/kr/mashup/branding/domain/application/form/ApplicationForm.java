package kr.mashup.branding.domain.application.form;

import kr.mashup.branding.domain.team.Team;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "ApplicationFormId")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationForm {
    @Id
    @GeneratedValue
    private Long ApplicationFormId;

    @ManyToOne
    private Team team;

    @OneToMany
    private List<Question> questions;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static ApplicationForm of(
            Team team,
            List<Question> questions
    ) {
        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.team = team;
        applicationForm.questions = questions;
        return applicationForm;
    }
}
