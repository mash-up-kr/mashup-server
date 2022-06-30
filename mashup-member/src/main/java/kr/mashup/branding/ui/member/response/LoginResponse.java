package kr.mashup.branding.ui.member.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

    private Long memberId;

    private String token;

    private String name;

    private String platform;

    private Integer generationNumber;

}
