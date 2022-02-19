package kr.mashup.branding.domain.adminmember;

import lombok.Value;

@Value(staticConstructor = "of")
public class AdminMemberLoginVo {
    String username;
    String password;
}
