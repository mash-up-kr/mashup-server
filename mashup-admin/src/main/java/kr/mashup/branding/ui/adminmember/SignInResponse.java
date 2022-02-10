package kr.mashup.branding.ui.adminmember;

import lombok.Data;

@Data
public class SignInResponse {
    private final String token;
    private final AdminMemberResponse adminMember;
}
