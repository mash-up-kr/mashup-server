package kr.mashup.branding.domain.danggn;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class DanggnScore {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_generation_id")
    private MemberGeneration memberGeneration;

    private Long totalShakeScore;

    @LastModifiedDate
    protected LocalDateTime lastShakedAt;

    public static DanggnScore of(
        MemberGeneration memberGeneration,
        Long totalShakeScore
    ) {
        return new DanggnScore(memberGeneration, totalShakeScore);
    }

    private DanggnScore(
        MemberGeneration memberGeneration,
        Long totalShakeScore
    ) {
        this.memberGeneration = memberGeneration;
        this.totalShakeScore = totalShakeScore;
    }

    public void addScore(Long score) {
        this.totalShakeScore += score;
    }
}
