package kr.mashup.branding.ui.danggn.response;

import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class DanggnScoreResponse {
    Long totalShakeScore;
}
