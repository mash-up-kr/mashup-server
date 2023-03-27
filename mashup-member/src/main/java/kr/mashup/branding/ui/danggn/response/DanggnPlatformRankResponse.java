package kr.mashup.branding.ui.danggn.response;

import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class DanggnPlatformRankResponse {
    Platform platform;

    Long totalShakeScore;
}
