package kr.mashup.branding.ui.invite;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InviteModifyRequest {

    private LocalDateTime validStartedAt;

    private LocalDateTime validEndedAt;
}
