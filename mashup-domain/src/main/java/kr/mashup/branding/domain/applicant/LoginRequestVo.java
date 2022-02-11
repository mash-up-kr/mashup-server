package kr.mashup.branding.domain.applicant;

import lombok.Value;

@Value(staticConstructor = "of")
public class LoginRequestVo {
    String email;
    String googleUserId;
}
