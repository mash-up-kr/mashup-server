package kr.mashup.branding.domain.danggn;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class DanggnScore {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_generation_id")
    private MemberGeneration memberGeneration;

    private Long totalShakeScore;

    private Long danggnRankingRoundId;

    @LastModifiedDate
    protected LocalDateTime lastShakedAt;

    public static DanggnScore of(
        MemberGeneration memberGeneration,
        Long totalShakeScore,
        Long danggnRankingRoundId
    ) {
        return new DanggnScore(memberGeneration, totalShakeScore, danggnRankingRoundId);
    }

    private DanggnScore(
        MemberGeneration memberGeneration,
        Long totalShakeScore,
        Long danggnRankingRoundId
    ) {
        this.memberGeneration = memberGeneration;
        this.totalShakeScore = totalShakeScore;
        this.danggnRankingRoundId = danggnRankingRoundId;
    }

    public void addScore(Long score) {
        this.totalShakeScore += score;
    }
}
