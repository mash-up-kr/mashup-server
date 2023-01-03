package kr.mashup.branding.ui.emailnotification;

import kr.mashup.branding.config.async.ThreadPoolName;
import kr.mashup.branding.domain.email.EmailMetadata;
import kr.mashup.branding.facade.emailnotification.EmailNotificationFacadeService;
import kr.mashup.branding.service.email.EmailResponse;
import kr.mashup.branding.service.email.EmailResponseStatus;
import kr.mashup.branding.service.email.EmailNotificationEvent;
import kr.mashup.branding.service.email.EmailNotificationService;
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

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationEventListener {

    private final EmailNotificationFacadeService emailNotificationFacadeService;
    private final EmailNotificationService emailNotificationService;
    private final EmailSendService emailSendService;

    @Async(value = ThreadPoolName.EMAIL_SEND_THREAD_POOL)
    @Transactional(propagation = Propagation.NEVER)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSendEmailEvent(EmailNotificationEvent event) {

        log.info("handle send email notification event with email notification id : {}",
            event.getEmailNotificationId());

        final Long emailNotificationId = event.getEmailNotificationId();

        final List<EmailMetadata> emailMetaDataList
            = emailNotificationFacadeService.getEmailMetaData(emailNotificationId);

        // infra 전송
        for(final EmailMetadata metadata : emailMetaDataList){
            try{
                final EmailResponse emailResponse = emailSendService.sendEmail(EmailSendVo.from(metadata));
                if(emailResponse.getStatus().equals(EmailResponseStatus.SUCCESS)){
                    emailNotificationService.updateToSuccess(metadata.getRequestId(), emailResponse.getMessageId());
                }else{
                    emailNotificationService.updateToFail(metadata.getRequestId());
                }
            }catch (Exception e){
                // 로깅 처리
                log.info("email send fail {}", e.getMessage());
                emailNotificationService.updateToFail(metadata.getRequestId());
            }
        }

    }

}
