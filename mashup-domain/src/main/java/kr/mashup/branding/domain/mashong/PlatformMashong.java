package kr.mashup.branding.domain.mashong;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformMashong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlatformMashongLevel level;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @ManyToOne(fetch = FetchType.LAZY)
    private Generation generation;

    private Long accumulatedPopcorn;

    public void feed(Long popcornCount, PlatformMashongLevel nextLevel) {
        if (accumulatedPopcorn + popcornCount >= level.getGoalPopcorn()) {
            level = nextLevel;
        }

        accumulatedPopcorn += popcornCount;
    }

    public boolean isMaxLevel() {
        return level.isMaxLevel();
    }

    public int getCurrentLevel() {
        return level.getLevel();
    }

    public Long getCurrentLevelGoalPopcorn() {
        return level.getGoalPopcorn();
    }
}
