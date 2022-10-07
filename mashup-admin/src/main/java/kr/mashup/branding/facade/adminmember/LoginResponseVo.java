package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import lombok.Value;

@Value(staticConstructor = "of")
public class LoginResponseVo {
    String token;
    AdminMemberVo adminMemberVo;
}
