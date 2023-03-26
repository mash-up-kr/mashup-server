package kr.mashup.branding.ui.danggn.response;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class DanggnScoreResponse {
    Long memberId;

    Long totalShakeScore;

    public static DanggnScoreResponse from(MemberGeneration memberGeneration) {
        return new DanggnScoreResponse(
            memberGeneration.getMember().getId(),
            memberGeneration.getDanggnScore().getTotalShakeScore()
        );
    }
}
