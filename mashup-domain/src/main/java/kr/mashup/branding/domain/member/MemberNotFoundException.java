package kr.mashup.branding.domain.member;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class MemberNotFoundException  extends NotFoundException {

    public MemberNotFoundException() {
        super(ResultCode.MEMBER_NOT_FOUND);
    }
}
