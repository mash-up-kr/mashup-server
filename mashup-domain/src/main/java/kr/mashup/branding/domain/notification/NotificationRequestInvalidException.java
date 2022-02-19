package kr.mashup.branding.domain.notification;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class NotificationRequestInvalidException extends BadRequestException {
    public NotificationRequestInvalidException(String message) {
        super(ResultCode.NOTIFICATION_REQUEST_INVALID, message);
    }
}
