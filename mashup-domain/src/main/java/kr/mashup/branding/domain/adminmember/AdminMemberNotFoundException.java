package kr.mashup.branding.domain.adminmember;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class AdminMemberNotFoundException extends NotFoundException {
    public AdminMemberNotFoundException() {
        super(ResultCode.ADMIN_MEMBER_NOT_FOUND);
    }
}
