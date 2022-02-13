package kr.mashup.branding.ui.adminmember;

import kr.mashup.branding.domain.adminmember.Position;
import lombok.Data;

@Data
public class AdminMemberResponse {
    private final Long adminMemberId;
    private final String username;
    private final Position position;
}
