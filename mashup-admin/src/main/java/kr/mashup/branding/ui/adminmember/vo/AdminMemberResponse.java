package kr.mashup.branding.ui.adminmember.vo;

import kr.mashup.branding.domain.adminmember.entity.Position;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class AdminMemberResponse {
    private Long adminMemberId;
    private String username;
    private Position position;
    private String phoneNumber;

    public static AdminMemberResponse from(AdminMemberVo adminMemberVo){
        return AdminMemberResponse.of(adminMemberVo.getAdminMemberId(), adminMemberVo.getUsername(), adminMemberVo.getPosition(), adminMemberVo.getPhoneNumber());
    }
}
