package kr.mashup.branding.ui.mashong.response;

import kr.mashup.branding.domain.mashong.PlatformMashongLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MashongLevelUpResponse {

    private final int currentLevel;
    private final boolean isLevelUp;

    public static MashongLevelUpResponse of(boolean isLevelUp, PlatformMashongLevel level) {
        return MashongLevelUpResponse.builder()
                .isLevelUp(isLevelUp)
                .currentLevel(level.getLevel())
                .build();
    }
}
