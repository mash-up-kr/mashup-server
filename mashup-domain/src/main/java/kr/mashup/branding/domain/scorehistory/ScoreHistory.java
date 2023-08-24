package kr.mashup.branding.domain.scorehistory;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
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
@Getter()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreHistory extends BaseEntity {

    @NotBlank
    private String type; // score type name ex)absent

    @NotBlank
    private String name;// score type desc ex. 전체세미나 결석

    @NotNull
    private Double score;

    @NotNull
    private LocalDateTime date; // 스케줄 시작시간 기준

    private String scheduleName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isCanceled;

    private String memo;


    public static ScoreHistory of(ScoreType scoreType, Schedule schedule, Member member) {

        return new ScoreHistory(
            scoreType.toString(),
            scoreType.getName(),
            scoreType.getScore(),
            schedule.getStartedAt(),
            schedule.getName(),
            schedule.getGeneration(),
            member,
            false,
            "");
    }

    public static ScoreHistory of(ScoreType scoreType,Member member, LocalDateTime date, String name, Generation generation, String memo) {

        return new ScoreHistory(
            scoreType.toString(),
            scoreType.getName(),
            scoreType.getScore(),
            date,
            name,
            generation,
            member,
            false,
            memo);
    }

    public void cancel(String memo){
        isCanceled = true;
        this.memo = memo;
    }

    private ScoreHistory(String type, String name, Double score, LocalDateTime date, String scheduleName,
                         Generation generation, Member member, boolean isCanceled, String memo) {
        this.type = type;
        this.name = name;
        this.score = score;
        this.date = date;
        this.scheduleName = scheduleName;
        this.generation = generation;
        this.member = member;
        this.isCanceled = isCanceled;
        this.memo = memo;
    }
}
