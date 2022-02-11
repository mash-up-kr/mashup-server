package kr.mashup.branding.ui.adminmember;

import kr.mashup.branding.ui.team.TeamResponse;
import lombok.Data;

@Data
public class AdminMemberResponse {
    private final Long adminMemberId;
    private final String username;
    private final TeamResponse team;
    private final String description;
    // TODO 역할 추가 예정
}
