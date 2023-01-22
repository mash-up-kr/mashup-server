package kr.mashup.branding.ui.pushnoti;

import kr.mashup.branding.config.ThreadPoolName;
import kr.mashup.branding.domain.pushnoti.vo.PushNotiSendVo;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PushNotiEventListener {
    private final PushNotiService pushNotiService;

    @Async(value = ThreadPoolName.PUSH_NOTI_SEND_THREAD_POOL)
    @Transactional(propagation = Propagation.NEVER)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSendPushNotiEvent(PushNotiSendVo event) {
        log.info("handle send push noti event");
        pushNotiService.sendPushNotification(event);
    }
}
