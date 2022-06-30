package kr.mashup.branding.ui.member.dto.request;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.member.Platform;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ValidInviteRequest {

    private Platform platform;

    private String inviteCode;


    public static ValidInviteRequest of(String platformName, String inviteCode){
        Platform platform = null;
        try{
            platform = Platform.valueOf(platformName);
        }catch (Exception e){
            throw new BadRequestException(ResultCode.BAD_REQUEST);
        }
        return new ValidInviteRequest(platform, inviteCode);
    }
}
