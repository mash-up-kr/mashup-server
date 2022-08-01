package kr.mashup.branding.domain.scorehistory;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreHistory extends BaseEntity {

    @NotBlank
    private String type;

    @NotBlank
    private String name;

    @NotNull
    private Double score;

    @NotNull
    private LocalDateTime date;

    private String scheduleName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    public static ScoreHistory of(String type, String name, Double score, LocalDateTime date, String scheduleName,
                                  Generation generation, Member member) {

        return new ScoreHistory(type, name, score, date, scheduleName, generation, member);
    }

    private ScoreHistory(String type, String name, Double score, LocalDateTime date, String scheduleName,
                         Generation generation, Member member) {
        this.type = type;
        this.name = name;
        this.score = score;
        this.date = date;
        this.scheduleName = scheduleName;
        this.generation = generation;
        this.member = member;
    }
}
