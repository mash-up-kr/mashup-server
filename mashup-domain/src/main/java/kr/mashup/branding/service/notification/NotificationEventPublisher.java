package kr.mashup.branding.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    // listener: NotificationEventListener
    public void publishNotificationEvent(NotificationEvent notificationEvent){
        eventPublisher.publishEvent(notificationEvent);
    }
}
