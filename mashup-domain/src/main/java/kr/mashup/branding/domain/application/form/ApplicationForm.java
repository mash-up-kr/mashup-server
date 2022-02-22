package kr.mashup.branding.domain.application.form;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.team.Team;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 설문지
 */
@Entity
@Getter
@ToString(of = {"applicationFormId", "name", "createdBy", "createdAt", "updatedBy", "updatedAt"})
@EqualsAndHashCode(of = "applicationFormId")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationForm {
    @Id
    @GeneratedValue
    private Long applicationFormId;

    @ManyToOne
    private Team team;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationForm")
    private final List<Question> questions = new ArrayList<>();

    private String name;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static ApplicationForm of(
        Team team,
        String name
    ) {
        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.team = team;
        applicationForm.name = name;
        return applicationForm;
    }

    public void addQuestions(List<Question> questions) {
        this.questions.addAll(questions);
    }

    /**
     * 요청 보내주는대로 덮어쓰기
     */
    public void update(UpdateApplicationFormVo updateApplicationFormVo) {
        questions.clear();
        questions.addAll(updateApplicationFormVo.getQuestions()
            .stream()
            .map(it -> Question.of(this, it))
            .collect(Collectors.toList())
        );
        name = updateApplicationFormVo.getName();
    }

    void setModifyInfo(String updatedBy, LocalDateTime updatedAt) {
        if (updatedAt != null && !updatedAt.isBefore(this.updatedAt)) {
            this.updatedBy = updatedBy;
            this.updatedAt = updatedAt;
        }

    }
}
