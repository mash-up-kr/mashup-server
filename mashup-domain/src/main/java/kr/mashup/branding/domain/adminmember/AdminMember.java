package kr.mashup.branding.domain.adminmember;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.mashup.branding.domain.team.Team;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
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

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static AdminMember of(
        String username,
        String password,
        String phoneNumber,
        Team team,
        String description
    ) {
        AdminMember adminMember = new AdminMember();
        adminMember.username = username;
        adminMember.password = password;
        adminMember.team = team;
        adminMember.phoneNumber = phoneNumber;
        adminMember.description = description;
        return adminMember;
    }
}
