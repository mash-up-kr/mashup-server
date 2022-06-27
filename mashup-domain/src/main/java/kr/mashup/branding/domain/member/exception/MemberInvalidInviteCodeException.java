package kr.mashup.branding.domain.member.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class MemberInvalidInviteCodeException extends BadRequestException {
    public MemberInvalidInviteCodeException() {
        super(ResultCode.MEMBER_INVALID_INVITE);
    }
}
