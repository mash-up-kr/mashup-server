package kr.mashup.branding.domain.scorehistory;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreHistory extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private ScoreType scoreType;

    @NotBlank
    private String scheduleName;

    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    public static ScoreHistory of(
        ScoreType scoreType,
        String scheduleName,
        LocalDate date,
        Generation generation,
        Member member) {

        return new ScoreHistory(scoreType, scheduleName, date, generation, member);
    }

    private ScoreHistory(ScoreType scoreType, String scheduleName, LocalDate date, Generation generation, Member member
    ) {
        this.scoreType = scoreType;
        this.scheduleName = scheduleName;
        this.date = date;
        this.generation = generation;
        this.member = member;
    }
}
