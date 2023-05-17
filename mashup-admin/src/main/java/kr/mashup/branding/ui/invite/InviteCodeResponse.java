package kr.mashup.branding.ui.invite;

import kr.mashup.branding.domain.invite.Invite;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InviteCodeResponse {

    private Long inviteCodeId;

    private String platform;

    private String inviteCode;

    private LocalDateTime validStartedAt;

    private LocalDateTime validEndedAt;


    public static InviteCodeResponse from(final Invite invite){
        return new InviteCodeResponse(
                invite.getId(),
                invite.getPlatform().toString(),
                invite.getCode(),
                invite.getStartedAt(),
                invite.getEndedAt()
        );
    }

    private InviteCodeResponse(Long inviteCodeId, String platform, String inviteCode, LocalDateTime validStartedAt, LocalDateTime validEndedAt) {
        this.inviteCodeId = inviteCodeId;
        this.platform = platform;
        this.inviteCode = inviteCode;
        this.validStartedAt = validStartedAt;
        this.validEndedAt = validEndedAt;
    }
}
