package kr.mashup.branding.domain.danggn;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DanggnNotificationPlatformRecord {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    private Long lastNotificationSentScore;

    public static DanggnNotificationPlatformRecord of(
        Generation generation,
        Platform platform,
        Long lastNotificationSentScore
    ) {
        return new DanggnNotificationPlatformRecord(generation, platform, lastNotificationSentScore);
    }

    private DanggnNotificationPlatformRecord(
        Generation generation,
        Platform platform,
        Long lastNotificationSentScore
    ) {
        this.generation = generation;
        this.platform = platform;
        this.lastNotificationSentScore = lastNotificationSentScore;
    }

    public void updateLastNotificationSentScore(Long lastNotificationSentScore) {
        this.lastNotificationSentScore = lastNotificationSentScore;
    }
}
