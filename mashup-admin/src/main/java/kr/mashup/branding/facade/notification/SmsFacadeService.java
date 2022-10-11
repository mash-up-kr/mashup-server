package kr.mashup.branding.facade.notification;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.sms.vo.SmsRecipientRequestVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsRequestVo;
import kr.mashup.branding.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SmsFacadeService {
    private final NotificationService notificationService;

    public SmsRequestVo getSmsMetaData(Long notificationId){
        Notification notification = notificationService.getNotification(notificationId);
        AdminMember sender = notification.getSender();
        return SmsRequestVo.of(
            notification.getMessageId(),
            sender.getPhoneNumber(),
            notification.getContent(),
            notification.getSmsRequests().stream()
                .map(it -> SmsRecipientRequestVo.of(
                    it.getMessageId(),
                    it.getRecipientPhoneNumber()
                ))
                .collect(Collectors.toList())
        );

    }
}
