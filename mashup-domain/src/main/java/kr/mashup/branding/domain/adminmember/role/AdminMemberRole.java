package kr.mashup.branding.domain.adminmember.role;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.adminmember.AdminMember;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "adminMemberRoleId")
@EntityListeners(AuditingEntityListener.class)
public class AdminMemberRole {

    @Id
    @GeneratedValue
    private Long adminMemberRoleId;

    @Enumerated(EnumType.STRING)
    private RoleGroup roleGroup;

    @Enumerated(EnumType.STRING)
    private RolePosition rolePosition;

    @OneToOne
    @JoinColumn
    private AdminMember adminMember;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static AdminMemberRole of(AdminMember adminMember, RoleGroup group, RolePosition position) {
        AdminMemberRole adminMemberRole = new AdminMemberRole();
        adminMemberRole.adminMember = adminMember;
        adminMemberRole.roleGroup = group;
        adminMemberRole.rolePosition = position;
        return adminMemberRole;
    }

}
