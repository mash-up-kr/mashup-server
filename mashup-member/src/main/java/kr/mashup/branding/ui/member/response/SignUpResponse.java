package kr.mashup.branding.ui.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class SignUpResponse {
    String token;
}
