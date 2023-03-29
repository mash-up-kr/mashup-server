package kr.mashup.branding.ui.danggn.response;

import kr.mashup.branding.domain.danggn.DanggnScore;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class DanggnMemberRankResponse {
    Long memberId;

    String memberName;

    Long totalShakeScore;

    public static DanggnMemberRankResponse from(DanggnScore danggnScore) {
        return new DanggnMemberRankResponse(
            danggnScore.getMemberGeneration().getMember().getId(),
            danggnScore.getMemberGeneration().getMember().getName(),
            danggnScore.getTotalShakeScore()
        );
    }
}
