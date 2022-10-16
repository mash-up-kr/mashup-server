package kr.mashup.branding.domain.team;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kr.mashup.branding.domain.generation.Generation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(of = {"teamId", "name", "createdBy", "createdAt", "updatedBy", "updatedAt", "generation"})
@EqualsAndHashCode(of = "teamId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Team {
    @Id
    @GeneratedValue
    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    private String name;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Team of(CreateTeamVo createTeamVo) {
        String name = createTeamVo.getName();
        Generation generation = createTeamVo.getGeneration();
        return new Team(generation, name);
    }

    private Team(Generation generation, String name) {
        this.generation = generation;
        this.name = name;
    }
}
