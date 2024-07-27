package kr.mashup.branding.service.mashong.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MashongMissionEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publish(MashongMissionEvent event) {
        eventPublisher.publishEvent(event);
    }
}
