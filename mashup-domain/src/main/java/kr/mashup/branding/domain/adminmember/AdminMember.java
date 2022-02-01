package kr.mashup.branding.domain.adminmember;

import kr.mashup.branding.domain.team.Team;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "adminMemberId")
@EntityListeners(AuditingEntityListener.class)
public class AdminMember {
    @Id
    @GeneratedValue
    private Long adminMemberId;

    private String providerUserId;

    private String name;

    @Column(nullable = true)
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

    public static AdminMember of(String providerUserId, String name, Team team, String description) {
        AdminMember adminMember = new AdminMember();
        adminMember.providerUserId = providerUserId;
        adminMember.name = name;
        adminMember.team = team;
        adminMember.description = description;
        return adminMember;
    }
}
