package kr.mashup.branding.domain.notification;

public class NotificationRequestInvalidException extends RuntimeException {
    public NotificationRequestInvalidException(String message) {
        super(message);
    }
}
