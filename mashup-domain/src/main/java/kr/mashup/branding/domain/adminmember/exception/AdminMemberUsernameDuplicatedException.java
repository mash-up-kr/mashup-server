package kr.mashup.branding.domain.adminmember.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class AdminMemberUsernameDuplicatedException extends BadRequestException {
    public AdminMemberUsernameDuplicatedException() {
        super(ResultCode.ADMIN_MEMBER_USERNAME_DUPLICATED);
    }
}
