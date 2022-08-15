package kr.mashup.branding.domain.member.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class MemberLoginFailException extends BadRequestException {
    public MemberLoginFailException() {
        super(ResultCode.MEMBER_NOT_MATCH_PASSWORD);
    }
}
