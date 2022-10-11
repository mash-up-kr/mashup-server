package kr.mashup.branding.ui.adminmember.vo;

import kr.mashup.branding.domain.adminmember.vo.AdminLoginCommand;
import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    public AdminLoginCommand toAdminMemberLoginVo(){
        return AdminLoginCommand.of(username, password);
    }
}
