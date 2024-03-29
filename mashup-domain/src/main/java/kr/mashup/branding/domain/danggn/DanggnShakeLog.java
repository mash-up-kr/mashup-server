package kr.mashup.branding.domain.danggn;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class DanggnShakeLog {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_generation_id")
    private MemberGeneration memberGeneration;

    private Long shakeScore;

    @CreatedDate
    protected LocalDateTime createdAt;

    public static DanggnShakeLog of(
        MemberGeneration memberGeneration,
        Long shakeScore
    ) {
        return new DanggnShakeLog(memberGeneration, shakeScore);
    }

    private DanggnShakeLog(
        MemberGeneration memberGeneration,
        Long shakeScore
    ) {
        this.memberGeneration = memberGeneration;
        this.shakeScore = shakeScore;
    }
}
