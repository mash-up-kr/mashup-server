package kr.mashup.branding.ui.adminmember;

import lombok.Data;

@Data
public class AdminMemberResponse {
    private final Long adminMemberId;
    private final String username;
    private final String description;
    // TODO 역할 추가 예정
}
