package kr.mashup.branding.ui.pushnoti;

import kr.mashup.branding.config.async.ThreadPoolName;
import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.pushnoti.vo.PushNotiSendVo;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiService;
import kr.mashup.branding.service.pushhistory.PushHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PushNotiEventListener {
    private final PushNotiService pushNotiService;
    private final PushHistoryService pushHistoryService;

    @Async(value = ThreadPoolName.PUSH_NOTI_SEND_THREAD_POOL)
    @Transactional(propagation = Propagation.NEVER)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSendPushNotiEvent(PushNotiSendVo event) {
        log.info("handle send push noti event");
        try{
            pushHistoryService.save(event);
        }catch (Exception ignored){
            log.error("[SAVE_HISTORY_FAIL] failed to save push noti history memberIds {} , title {} , body {}",
                    extractMemberIds(event), event.getTitle(), event.getBody());
        }
        try{
            pushNotiService.sendPushNotification(event);
        }catch (Exception ignored){
            log.error("[SEND_PUSH_FAIL] failed to send push memberIds {} , title {} , body {}",
                    extractMemberIds(event), event.getTitle(), event.getBody());
        }
    }

    private static List<Long> extractMemberIds(PushNotiSendVo event) {
        return event.getMembers().stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

}
