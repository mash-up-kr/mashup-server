package kr.mashup.branding.domain.mashong;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.service.mashong.dto.LevelUpResult;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private PlatformMashongLevel level;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Generation generation;

    private Long accumulatedPopcorn;

    public void feed(Long popcornCount) {
        accumulatedPopcorn += popcornCount;
    }

    public boolean isMaxLevel() {
        return level.isMaxLevel();
    }

    public boolean isSameLevel(PlatformMashongLevel level) {
        return getCurrentLevel() == level.getLevel();
    }

    public LevelUpResult levelUp(PlatformMashongLevel goalLevel) {
        if (accumulatedPopcorn < goalLevel.getGoalPopcorn()) {
            return LevelUpResult.FAIL;
        }

        level = goalLevel;
        accumulatedPopcorn -= goalLevel.getGoalPopcorn();
        return LevelUpResult.SUCCESS;
    }

    public int getCurrentLevel() {
        return level.getLevel();
    }

    public Long getCurrentLevelGoalPopcorn() {
        return level.getGoalPopcorn();
    }
}
