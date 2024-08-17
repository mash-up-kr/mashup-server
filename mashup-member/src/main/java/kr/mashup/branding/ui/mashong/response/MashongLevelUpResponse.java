package kr.mashup.branding.ui.mashong.response;

import kr.mashup.branding.domain.mashong.PlatformMashongLevel;
import kr.mashup.branding.service.mashong.dto.LevelUpResult;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MashongLevelUpResponse {

    private final int currentLevel;
    private final boolean isLevelUp;

    public static MashongLevelUpResponse of(LevelUpResult levelUpResult, PlatformMashongLevel level) {
        return MashongLevelUpResponse.builder()
                .isLevelUp(levelUpResult.isLevelUp())
                .currentLevel(level.getLevel())
                .build();
    }
}
