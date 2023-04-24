package kr.mashup.branding.domain.popup.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class MemberPopupNotFoundException extends NotFoundException {

    public MemberPopupNotFoundException() {
        super(ResultCode.MEMBER_POPUP_NOT_FOUND);
    }
}
