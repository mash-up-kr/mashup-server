package kr.mashup.branding.domain.adminmember;

import lombok.Value;

@Value(staticConstructor = "of")
public class AdminMemberVo {
    String providerUserId;
    String name;
    Long teamId;
    String description;
}
