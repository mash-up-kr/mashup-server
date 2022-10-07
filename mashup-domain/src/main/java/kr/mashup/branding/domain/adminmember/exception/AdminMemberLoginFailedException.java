package kr.mashup.branding.domain.adminmember.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class AdminMemberLoginFailedException extends BadRequestException {
    public AdminMemberLoginFailedException() {
        super(ResultCode.ADMIN_MEMBER_LOGIN_FAILED);
    }
}
