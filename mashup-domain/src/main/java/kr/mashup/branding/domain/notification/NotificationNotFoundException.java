package kr.mashup.branding.domain.notification;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class NotificationNotFoundException extends NotFoundException {
    public NotificationNotFoundException() {
        super(ResultCode.NOTIFICATION_NOT_FOUND);
    }

    public NotificationNotFoundException(Long notificationId) {
        super(ResultCode.NOTIFICATION_NOT_FOUND, "Notification not found. notificationId: " + notificationId);
    }
}
