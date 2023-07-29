package kr.mashup.branding.domain.adminmember.vo;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.entity.Position;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Getter
@Value(staticConstructor = "of")
public class AdminMemberVo {

    Long adminMemberId;

    String username;

    Position position;

    String createdBy;

    LocalDateTime createdAt;

    String updatedBy;

    LocalDateTime updatedAt;

    public static AdminMemberVo from(AdminMember adminMember){
        return AdminMemberVo.of(
            adminMember.getAdminMemberId(),
            adminMember.getUsername(),
            adminMember.getPosition(),
            adminMember.getCreatedBy(),
            adminMember.getCreatedAt(),
            adminMember.getUpdatedBy(),
            adminMember.getUpdatedAt());
    }
}
