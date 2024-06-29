package kr.mashup.branding.ui.mashong.response;

import kr.mashup.branding.domain.mashong.MashongPopcorn;
import kr.mashup.branding.domain.mashong.PlatformMashong;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class PlatformMashongStatusResponse {

    private final int currentLevel;
    private final Long goalPopcornValue;
    private final Long accumulatedPopcornValue;
    private final Long lastPopcornValue;

    public static PlatformMashongStatusResponse of(PlatformMashong platformMashong, MashongPopcorn mashongPopcorn) {
        return PlatformMashongStatusResponse.builder()
                .currentLevel(platformMashong.getCurrentLevel())
                .goalPopcornValue(platformMashong.getCurrentGoalPopcornCount())
                .accumulatedPopcornValue(platformMashong.getAccumulatedPopcorn())
                .lastPopcornValue(mashongPopcorn.getPopcorn())
                .build();
    }
}
