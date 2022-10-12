package kr.mashup.branding.facade.notification;

import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.exception.NotificationRequestInvalidException;
import kr.mashup.branding.domain.notification.sms.vo.SmsRequestVo;
import kr.mashup.branding.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SmsFacadeService {
    private final NotificationService notificationService;

    public SmsRequestVo getSmsMetaData(Long notificationId){

        final Notification notification = notificationService.getNotification(notificationId);
        if(!notification.getSender().getPhoneNumberRegistered()){
            throw new NotificationRequestInvalidException(
                "Sender's phoneNumber must be registered to NHN Cloud Notification Service");
        }
        return SmsRequestVo.from(notification);

    }
}
