package kr.mashup.branding.domain.adminmember;

import lombok.Value;

@Value(staticConstructor = "of")
public class AdminMemberVo {
    private String providerUserId;
    private String name;
    private Long teamId;
    private String description;
}
