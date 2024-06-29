package kr.mashup.branding.service.mashong.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum LevelUpResult {

    SUCCESS(true, true),
    DUPLICATED(true, false),
    FAIL(false, false),
    ;

    private final boolean isLevelUp;
    private final boolean isUpdateLog;

    public boolean isLevelUpResult() {
        return List.of(SUCCESS, DUPLICATED).contains(this);
    }
}
