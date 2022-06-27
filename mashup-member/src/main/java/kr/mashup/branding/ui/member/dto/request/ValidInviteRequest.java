package kr.mashup.branding.ui.member.dto.request;

import kr.mashup.branding.domain.member.Platform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ValidInviteRequest {

    private Platform platform;

    private String inviteCode;
}
