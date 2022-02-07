package kr.mashup.branding.domain.adminmember;

import lombok.Value;

@Value(staticConstructor = "of")
public class AdminMemberVo {
    String username;
    String password;
    AdminMemberPosition position;
    Long teamId;
    String description;
}
