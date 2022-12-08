package kr.mashup.branding.service.notification;

import lombok.Value;

@Value(staticConstructor = "of")
public class NotificationEvent {
    Long notificationId;
    NotificationEventType type;
}
