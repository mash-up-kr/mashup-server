package kr.mashup.branding.domain.adminmember.vo;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.entity.Position;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode @ToString
public class AdminMemberVo {

    private final Long adminMemberId;

    private final String username;

    private final String phoneNumber;

    private final Boolean phoneNumberRegistered = false;

    private final Position position;

    private final String createdBy;

    private final LocalDateTime createdAt;

    private final String updatedBy;

    private final LocalDateTime updatedAt;

    public static AdminMemberVo from(AdminMember adminMember){
        return AdminMemberVo.of(
            adminMember.getAdminMemberId(),
            adminMember.getUsername(),
            adminMember.getPhoneNumber(),
            adminMember.getPosition(),
            adminMember.getCreatedBy(),
            adminMember.getCreatedAt(),
            adminMember.getUpdatedBy(),
            adminMember.getUpdatedAt());
    }
}
