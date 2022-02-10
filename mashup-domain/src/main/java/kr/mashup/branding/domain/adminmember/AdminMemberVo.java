package kr.mashup.branding.domain.adminmember;

import lombok.Value;

@Value(staticConstructor = "of")
public class AdminMemberVo {
    String username;
    String password;
    String phoneNumber;
    Long teamId;
    String description;
}
