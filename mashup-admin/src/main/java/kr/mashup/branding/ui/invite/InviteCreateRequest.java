package kr.mashup.branding.ui.invite;

import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InviteCreateRequest {

    private Integer generationNumber;

    private Platform platform;

    private LocalDateTime validStartedAt;

    private LocalDateTime validEndedAt;
}
