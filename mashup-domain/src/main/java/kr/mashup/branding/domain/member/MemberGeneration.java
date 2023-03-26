package kr.mashup.branding.domain.member;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.generation.Generation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGeneration extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "memberGeneration", orphanRemoval = true)
    private DanggnScore danggnScore;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    public static MemberGeneration of(Member member, Generation generation, Platform platform){
        return new MemberGeneration(member, generation, platform);
    }

    private MemberGeneration(Member member, Generation generation, Platform platform) {
        Assert.notNull(member, "member should not be null");
        Assert.notNull(generation, "generation should not be null");
        Assert.notNull(platform, "platform should not be null");
        this.member = member;
        this.generation = generation;
        this.platform = platform;
    }

    public DanggnScore getDanggnScore() {
        if (danggnScore == null) {
            this.danggnScore = DanggnScore.of(this, 0L);
        }
        return this.danggnScore;
    }
}
