package kr.mashup.branding.domain.email;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EmailTemplate {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private EmailTemplateName templateName;

    private String title;

    private String body;
}
