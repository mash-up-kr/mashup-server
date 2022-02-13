package kr.mashup.branding.domain.adminmember;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.adminmember.role.AdminMemberRole;
import kr.mashup.branding.domain.adminmember.role.RoleGroup;
import kr.mashup.branding.domain.adminmember.role.RolePosition;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(of = {"adminMemberId", "username", "phoneNumber", "createdAt", "updatedAt"})
@EqualsAndHashCode(of = "adminMemberId")
@EntityListeners(AuditingEntityListener.class)
public class AdminMember {
    @Id
    @GeneratedValue
    private Long adminMemberId;

    private String username;

    private String password;

    private String phoneNumber;

    private Boolean phoneNumberRegistered = false;

    @OneToOne(cascade = CascadeType.ALL)
    private AdminMemberRole role;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static AdminMember of(
        String username,
        String password,
        String phoneNumber,
        RoleGroup group,
        RolePosition position
    ) {
        AdminMember adminMember = new AdminMember();
        adminMember.username = username;
        adminMember.password = password;
        adminMember.phoneNumber = phoneNumber;
        adminMember.role = AdminMemberRole.of(adminMember, group, position);
        return adminMember;
    }
}
