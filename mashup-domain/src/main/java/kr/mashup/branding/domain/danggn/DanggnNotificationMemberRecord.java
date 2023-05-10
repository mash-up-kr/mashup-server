package kr.mashup.branding.domain.danggn;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_generation_id")
    private MemberGeneration memberGeneration;

    private Long lastNotificationSentScore;

    public static DanggnNotificationMemberRecord of(
        MemberGeneration memberGeneration,
        Long lastNotificationSentScore
    ) {
        return new DanggnNotificationMemberRecord(memberGeneration, lastNotificationSentScore);
    }

    private DanggnNotificationMemberRecord(
        MemberGeneration memberGeneration,
        Long lastNotificationSentScore
    ) {
        this.memberGeneration = memberGeneration;
        this.lastNotificationSentScore = lastNotificationSentScore;
    }

    public void updateLastNotificationSentScore(Long lastNotificationSentScore) {
        this.lastNotificationSentScore = lastNotificationSentScore;
    }
}
