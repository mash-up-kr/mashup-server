package kr.mashup.branding.infrastructure.pushnoti;

import kr.mashup.branding.domain.pushnoti.vo.PushNotiSendVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PushNotiEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishPushNotiSendEvent(PushNotiSendVo pushNotiSendEvent){
        eventPublisher.publishEvent(pushNotiSendEvent);
    }
}
