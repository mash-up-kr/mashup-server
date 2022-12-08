package kr.mashup.branding.domain.applicant.vo;

import lombok.Value;

@Value(staticConstructor = "of")
public class LoginRequestVo {
    String googleIdToken;
}
