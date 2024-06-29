package kr.mashup.branding.domain.birthday;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BirthdayCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderMemberId;

    private String senderMemberName;

    @Enumerated(EnumType.STRING)
    private Platform senderMemberPlatform;

    private Long recipientMemberId;

    private Long generationId;

    private String message;

    private String imageUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    public BirthdayCard(
        Long recipientMemberId,
        Member senderMember,
        MemberGeneration senderMemberGeneration,
        String message,
        String imageUrl
    ) {
        this.senderMemberId = senderMember.getId();
        this.senderMemberName = senderMember.getName();
        this.senderMemberPlatform = senderMemberGeneration.getPlatform();
        this.recipientMemberId = recipientMemberId;
        this.generationId = senderMemberGeneration.getGeneration().getId();
        this.message = message;
        this.imageUrl = imageUrl;
    }
}
