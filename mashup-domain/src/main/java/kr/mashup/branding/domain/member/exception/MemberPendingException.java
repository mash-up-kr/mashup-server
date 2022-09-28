package kr.mashup.branding.domain.member.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class MemberPendingException  extends NotFoundException {

    public MemberPendingException() {
        super(ResultCode.MEMBER_PENDING_STATUS);
    }
}
