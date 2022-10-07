package kr.mashup.branding.ui.adminmember.vo;

import kr.mashup.branding.domain.adminmember.vo.AdminMemberLoginCommand;
import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    public AdminMemberLoginCommand toAdminMemberLoginVo(){
        return AdminMemberLoginCommand.of(username, password);
    }
}
