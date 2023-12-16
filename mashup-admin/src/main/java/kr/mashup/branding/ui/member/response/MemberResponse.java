package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.domain.member.CurrentMemberStatus;
import kr.mashup.branding.domain.member.MemberStatus;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class MemberResponse {
    private Long memberId;
    private String name;
    private String identification;
    private String platform;
    private Double score;
    private CurrentMemberStatus memberStatus;

}
