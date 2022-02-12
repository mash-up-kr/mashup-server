package kr.mashup.branding.domain.adminmember;

import kr.mashup.branding.domain.adminmember.role.RoleGroup;
import kr.mashup.branding.domain.adminmember.role.RolePosition;
import lombok.Value;

@Value(staticConstructor = "of")
public class AdminMemberVo {
    String username;
    String password;
    String phoneNumber;
    String description;
    RoleGroup group;
    RolePosition position;
}
