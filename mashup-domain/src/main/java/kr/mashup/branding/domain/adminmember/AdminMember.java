package kr.mashup.branding.domain.adminmember;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(of = {"adminMemberId", "username", "phoneNumber", "position", "createdAt", "updatedAt"})
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

    @Enumerated(EnumType.STRING)
    private Position position;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static AdminMember of(
        String username,
        String password,
        String phoneNumber,
        Position position
    ) {
        AdminMember adminMember = new AdminMember();
        adminMember.username = username;
        adminMember.password = password;
        adminMember.phoneNumber = phoneNumber;
        adminMember.position = position;
        return adminMember;
    }
}
