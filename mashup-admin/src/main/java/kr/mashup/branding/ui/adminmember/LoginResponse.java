package kr.mashup.branding.ui.adminmember;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private AdminMemberResponse adminMember;
}
