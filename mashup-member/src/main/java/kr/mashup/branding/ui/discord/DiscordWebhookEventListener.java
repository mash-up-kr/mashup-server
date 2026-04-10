package kr.mashup.branding.ui.discord;

import kr.mashup.branding.config.async.ThreadPoolName;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceDiscordVo;
import kr.mashup.branding.infrastructure.discord.DiscordWebhookService;
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
public class DiscordWebhookEventListener {

    private final DiscordWebhookService discordWebhookService;

    @Async(value = ThreadPoolName.PUSH_NOTI_SEND_THREAD_POOL)
    @Transactional(propagation = Propagation.NEVER)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDiscordWebhookEvent(AttendanceDiscordVo event) {
        try {
            discordWebhookService.send(event.getContent());
        } catch (Exception e) {
            log.error("[DISCORD_WEBHOOK_FAIL] failed to send: {}", e.getMessage());
        }
    }
}
