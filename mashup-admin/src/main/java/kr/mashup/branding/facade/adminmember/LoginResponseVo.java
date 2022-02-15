package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.adminmember.AdminMember;
import lombok.Value;

@Value(staticConstructor = "of")
public class LoginResponseVo {
    String token;
    AdminMember adminMember;
}
