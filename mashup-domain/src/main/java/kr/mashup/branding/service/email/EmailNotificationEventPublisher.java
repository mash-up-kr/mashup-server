package kr.mashup.branding.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishEmailNotificationEvent(EmailNotificationEvent emailNotificationEvent){
        eventPublisher.publishEvent(emailNotificationEvent);
    }
}
