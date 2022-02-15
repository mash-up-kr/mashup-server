package kr.mashup.branding.ui.adminmember;

import kr.mashup.branding.domain.adminmember.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminMemberResponse {
    private Long adminMemberId;
    private String username;
    private Position position;
}
