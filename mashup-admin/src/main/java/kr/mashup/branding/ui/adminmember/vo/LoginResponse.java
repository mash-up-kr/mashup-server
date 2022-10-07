package kr.mashup.branding.ui.adminmember.vo;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import kr.mashup.branding.facade.adminmember.LoginResponseVo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private AdminMemberResponse adminMemberResponse;

    public static LoginResponse from(LoginResponseVo loginResponseVo){
        AdminMemberVo adminMemberVo = loginResponseVo.getAdminMemberVo();
        AdminMemberResponse adminMemberResponse = AdminMemberResponse.of(adminMemberVo.getAdminMemberId(), adminMemberVo.getUsername(), adminMemberVo.getPosition(), adminMemberVo.getPhoneNumber());
        return new LoginResponse(loginResponseVo.getToken(), adminMemberResponse);
    }
}
