package kr.mashup.branding.facade.notification;

import kr.mashup.branding.domain.notification.Notification;
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

        return SmsRequestVo.from(notification);

    }
}
