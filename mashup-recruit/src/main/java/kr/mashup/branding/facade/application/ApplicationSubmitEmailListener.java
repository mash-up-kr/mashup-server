package kr.mashup.branding.facade.application;

import kr.mashup.branding.config.async.ThreadPoolName;
import kr.mashup.branding.service.email.EmailSendService;
import kr.mashup.branding.service.email.EmailSendVo;
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
public class ApplicationSubmitEmailListener {

    private final EmailSendService emailSendService;

    @Async(value = ThreadPoolName.EMAIL_SEND_THREAD_POOL)
    @Transactional(propagation = Propagation.NEVER)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSendEmailEvent(EmailSendEvent event) {

        try {
            final EmailSendVo emailSendVo
                = EmailSendVo.of(event.getEmailTemplateName(), event.getBindingData(), event.getSenderEmail(), event.getReceiverEmail());
            emailSendService.sendEmail(emailSendVo);
        } catch (Exception e) {
            log.error("submit email send fail to {} caused by {}", event.getReceiverEmail(), e.getMessage());
        }

    }


}
