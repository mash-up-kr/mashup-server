package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "applicationId")
    private List<Answer> answers;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Application of(
            ApplicationForm applicationForm,
            List<Answer> answers
    ) {
        Application application = new Application();
        application.applicationForm = applicationForm;
        application.answers = answers;
        return application;
    }
}
