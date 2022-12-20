package kr.mashup.branding.ui.emailnotification;

import kr.mashup.branding.config.async.ThreadPoolName;
import kr.mashup.branding.domain.email.EmailMetadata;
import kr.mashup.branding.domain.email.EmailRequestStatus;
import kr.mashup.branding.facade.emailnotification.EmailNotificationFacadeService;
import kr.mashup.branding.infrastructure.email.SesEmailService;
import kr.mashup.branding.infrastructure.email.SesResponseStatus;
import kr.mashup.branding.service.email.EmailNotificationEvent;
import kr.mashup.branding.service.email.EmailNotificationService;
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
    private final SesEmailService sesEmailService;

    // 이메일 발송 비동기 이벤트 처리 로직
    @Async(value = ThreadPoolName.SMS_ASYNC_POOL)
    @Transactional(propagation = Propagation.NEVER)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSendSms(EmailNotificationEvent event) {

        log.info("handle send email notification event with email notification id : {}",
            event.getEmailNotificationId());

        final Long emailNotificationId = event.getEmailNotificationId();
        // List<email request id, template name, binding data, src, dest> 가져오기
        final List<EmailMetadata> emailMetaDataList
            = emailNotificationFacadeService.getEmailMetaData(emailNotificationId);

        // infra 전송
        for(final EmailMetadata metadata : emailMetaDataList){
            try{
                final SesResponseStatus status = sesEmailService.sendEmail(metadata);
                if(status.equals(SesResponseStatus.SUCCESS)){
                    emailNotificationService.update(metadata.getRequestId(), EmailRequestStatus.SUCCESS);
                }else{
                    emailNotificationService.update(metadata.getRequestId(), EmailRequestStatus.FAIL);
                }
            }catch (Exception e){
                // 로깅 처리
                log.info("email send fail {}", e.getMessage());
                emailNotificationService.update(metadata.getRequestId(), EmailRequestStatus.FAIL);
            }
        }

    }

}
