package kr.mashup.branding.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberAuth {
    private Long memberId;
}
