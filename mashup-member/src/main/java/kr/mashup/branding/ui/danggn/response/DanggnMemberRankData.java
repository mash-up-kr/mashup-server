package kr.mashup.branding.ui.danggn.response;

import kr.mashup.branding.domain.danggn.DanggnScore;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class DanggnMemberRankData {
    Long memberId;

    String memberName;

    Long totalShakeScore;

    public static DanggnMemberRankData from(DanggnScore danggnScore) {
        return new DanggnMemberRankData(
            danggnScore.getMemberGeneration().getMember().getId(),
            danggnScore.getMemberGeneration().getMember().getName(),
            danggnScore.getTotalShakeScore()
        );
    }
}
