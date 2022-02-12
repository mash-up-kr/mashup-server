package kr.mashup.branding.ui.adminmember;

import kr.mashup.branding.domain.adminmember.role.RoleGroup;
import kr.mashup.branding.domain.adminmember.role.RolePosition;
import lombok.Data;

@Data
public class AdminMemberResponse {
    private final Long adminMemberId;
    private final String username;
    private final String description;
    private final RoleGroup group;
    private final RolePosition position;
}
