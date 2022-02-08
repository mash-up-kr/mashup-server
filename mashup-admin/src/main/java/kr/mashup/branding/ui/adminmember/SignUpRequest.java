package kr.mashup.branding.ui.adminmember;

import kr.mashup.branding.domain.adminmember.AdminMemberPosition;
import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private AdminMemberPosition position;
    private String phoneNumber;
    private Long teamId;
    private String description;
}
