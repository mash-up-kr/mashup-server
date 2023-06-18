package kr.mashup.branding.domain.danggn;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DanggnNotificationMemberRecord {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_generation_id")
    private MemberGeneration memberGeneration;

    private Long lastNotificationSentScore;

    private Long danggnRankingRoundId;

    public static DanggnNotificationMemberRecord of(
        MemberGeneration memberGeneration,
        Long lastNotificationSentScore,
        Long danggnRankingRoundId
    ) {
        return new DanggnNotificationMemberRecord(memberGeneration, lastNotificationSentScore, danggnRankingRoundId);
    }

    private DanggnNotificationMemberRecord(
        MemberGeneration memberGeneration,
        Long lastNotificationSentScore,
        Long danggnRankingRoundId
    ) {
        this.memberGeneration = memberGeneration;
        this.lastNotificationSentScore = lastNotificationSentScore;
        this.danggnRankingRoundId = danggnRankingRoundId;
    }

    public void updateLastNotificationSentScore(Long lastNotificationSentScore) {
        this.lastNotificationSentScore = lastNotificationSentScore;
    }
}
