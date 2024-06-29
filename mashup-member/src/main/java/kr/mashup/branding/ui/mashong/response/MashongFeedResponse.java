package kr.mashup.branding.ui.mashong.response;

import kr.mashup.branding.domain.mashong.MashongPopcorn;
import kr.mashup.branding.domain.mashong.PlatformMashong;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MashongFeedResponse {

    private final boolean isFed;
    private final Long lastPopcornValue;
    private final Long currentLevelGoalPopcorn;
    private final Long accumulatedPopcornValue;
    private final int currentLevel;

    public static MashongFeedResponse of(boolean isFed, PlatformMashong platformMashong, MashongPopcorn mashongPopcorn) {
        return MashongFeedResponse.builder()
                .isFed(isFed)
                .currentLevelGoalPopcorn(platformMashong.getCurrentLevelGoalPopcorn())
                .lastPopcornValue(mashongPopcorn.getPopcorn())
                .currentLevel(platformMashong.getCurrentLevel())
                .accumulatedPopcornValue(platformMashong.getAccumulatedPopcorn())
                .build();
    }
}
