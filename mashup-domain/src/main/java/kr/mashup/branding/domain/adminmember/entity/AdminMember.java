package kr.mashup.branding.domain.adminmember.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString(of = {"adminMemberId", "username", "position", "createdAt", "updatedAt"})
@EqualsAndHashCode(of = "adminMemberId")
@EntityListeners(AuditingEntityListener.class)
public class AdminMember {
    @Id
    @GeneratedValue
    private Long adminMemberId;

    private String username;

    private String password;

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
        Position position
    ) {
        AdminMember adminMember = new AdminMember();
        adminMember.username = username;
        adminMember.password = password;
        adminMember.position = position;
        return adminMember;
    }

    public void setPassword(PasswordEncoder encoder, String resetPassword){
        this.password = encoder.encode(resetPassword);
    }
}
