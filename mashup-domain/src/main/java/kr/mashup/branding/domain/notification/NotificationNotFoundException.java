package kr.mashup.branding.domain.notification;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException() {
        super();
    }

    public NotificationNotFoundException(Long notificationId) {
        super("Notification not found. notificationId: " + notificationId);
    }
}
