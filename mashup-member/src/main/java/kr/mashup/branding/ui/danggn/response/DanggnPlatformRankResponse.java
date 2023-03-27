package kr.mashup.branding.ui.danggn.response;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class DanggnPlatformRankResponse {
    Long memberId;

    String memberName;

    Long totalShakeScore;

    public static DanggnPlatformRankResponse from(MemberGeneration memberGeneration) {
        return new DanggnPlatformRankResponse(
            memberGeneration.getMember().getId(),
            memberGeneration.getMember().getName(),
            memberGeneration.getDanggnScore().getTotalShakeScore()
        );
    }
}
