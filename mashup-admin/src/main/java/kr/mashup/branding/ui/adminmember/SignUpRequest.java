package kr.mashup.branding.ui.adminmember;

import kr.mashup.branding.domain.adminmember.role.RoleGroup;
import kr.mashup.branding.domain.adminmember.role.RolePosition;
import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String phoneNumber;
    private String description;
    private RoleGroup group;
    private RolePosition position;
}
