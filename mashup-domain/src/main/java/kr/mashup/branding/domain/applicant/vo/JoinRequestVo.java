package kr.mashup.branding.domain.applicant.vo;

import lombok.Value;

@Value(staticConstructor = "of")
public class JoinRequestVo {
    String email;
    String googleUserId;
}
