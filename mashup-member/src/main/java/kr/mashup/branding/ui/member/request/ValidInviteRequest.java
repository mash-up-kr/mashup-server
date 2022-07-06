package kr.mashup.branding.ui.member.request;

import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidInviteRequest {

    private Platform platform;

    private String inviteCode;
}
