package kr.mashup.branding.domain.application.form;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.team.Team;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static ApplicationForm of(
        Team team,
        List<Question> questions,
        String name
    ) {
        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.team = team;
        applicationForm.questions = questions;
        applicationForm.name = name;
        return applicationForm;
    }
}
