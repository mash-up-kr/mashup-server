package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class AdminMemberLoginFailedException extends BadRequestException {
    public AdminMemberLoginFailedException() {
        super(ResultCode.ADMIN_MEMBER_LOGIN_FAILED);
    }
}
