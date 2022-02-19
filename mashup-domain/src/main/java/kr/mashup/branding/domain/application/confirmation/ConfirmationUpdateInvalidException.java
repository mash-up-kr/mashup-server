package kr.mashup.branding.domain.application.confirmation;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ConfirmationUpdateInvalidException extends BadRequestException {
    public ConfirmationUpdateInvalidException() {
        super(ResultCode.APPLICATION_CONFIRMATION_UPDATE_INVALID);
    }
}
