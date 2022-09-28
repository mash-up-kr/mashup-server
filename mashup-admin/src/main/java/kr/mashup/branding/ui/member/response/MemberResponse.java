package kr.mashup.branding.ui.member.response;

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
}
