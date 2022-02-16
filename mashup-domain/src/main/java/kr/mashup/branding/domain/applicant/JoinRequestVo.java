package kr.mashup.branding.domain.applicant;

import lombok.Value;

@Value(staticConstructor = "of")
public class JoinRequestVo {
    String email;
    String googleUserId;
}
