package kr.mashup.branding.domain.invite.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class InviteNotFoundException extends NotFoundException {

    public InviteNotFoundException() {
        super(ResultCode.INVITE_NOT_FOUND);
    }
}