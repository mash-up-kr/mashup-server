package kr.mashup.branding.ui.danggn.response;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class DanggnMemberRankResponse {
    Long memberId;

    String memberName;

    Long totalShakeScore;

    public static DanggnMemberRankResponse from(MemberGeneration memberGeneration) {
        return new DanggnMemberRankResponse(
            memberGeneration.getMember().getId(),
            memberGeneration.getMember().getName(),
            memberGeneration.getDanggnScore().getTotalShakeScore()
        );
    }
}
