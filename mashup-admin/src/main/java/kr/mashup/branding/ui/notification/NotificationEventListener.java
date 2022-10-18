package kr.mashup.branding.ui.notification;

import kr.mashup.branding.config.async.ThreadPoolName;
import kr.mashup.branding.domain.notification.sms.vo.SmsRequestVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendResultVo;
import kr.mashup.branding.facade.notification.NotificationFacadeService;
import kr.mashup.branding.facade.notification.SmsFacadeService;
import kr.mashup.branding.service.notification.NotificationEvent;
import kr.mashup.branding.service.notification.sms.SmsService;
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
public class NotificationEventListener {

    private final SmsFacadeService smsFacadeService;
    private final NotificationFacadeService notificationFacadeService;
    private final SmsService smsService;

    // 문자 발송 비동기 이벤트 처리 로직
    @Async(value = ThreadPoolName.SMS_ASYNC_POOL)
    @Transactional(propagation = Propagation.NEVER)
    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT,
        condition = "#{${event.type} eq T(kr.mashup.branding.service.notification.NotificationEventType).CREATED }")
    public void handleSendSms(NotificationEvent event){
        log.info("handle send sms notification event with notification id : {}", event.getNotificationId());
        final Long notificationId = event.getNotificationId();
        final SmsRequestVo smsRequestVo = smsFacadeService.getSmsMetaData(notificationId);

        SmsSendResultVo smsSendResultVo;

        try {
            smsSendResultVo = smsService.send(smsRequestVo);
        } catch (Exception e) {
            log.error("Failed to send sms. notificationId: {}", notificationId, e);
            smsSendResultVo = SmsSendResultVo.UNKNOWN;
        }

        notificationFacadeService.updateSmsStatus(notificationId,smsSendResultVo);
    }



}
