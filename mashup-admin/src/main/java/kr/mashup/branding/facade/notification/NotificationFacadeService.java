package kr.mashup.branding.facade.notification;

import java.util.stream.Collectors;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.vo.NotificationDetailVo;
import kr.mashup.branding.service.notification.NotificationService;
import kr.mashup.branding.domain.notification.sms.vo.SmsRecipientRequestVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsRequestVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendRequestVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendResultVo;
import kr.mashup.branding.service.notification.sms.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationFacadeService {
    private final AdminMemberService adminMemberService;
    private final NotificationService notificationService;
    private final SmsService smsService;

    public NotificationDetailVo sendSms(Long adminMemberId, SmsSendRequestVo smsSendRequestVo) {
        AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        // 요청 정보 생성
        NotificationDetailVo notificationDetailVo = notificationService.create(adminMember, smsSendRequestVo);
        // 문자 발송 API 호출
        SmsSendResultVo smsSendResultVo = sendSms(notificationDetailVo);
        // 결과 상태 변경 (성공, 실패, 전송중, 알수없음 등)
        notificationService.update(notificationDetailVo.getNotification().getNotificationId(), smsSendResultVo);
        return notificationDetailVo;
    }

    private SmsSendResultVo sendSms(NotificationDetailVo notificationDetailVo) {
        try {
            return smsService.send(toSmsRequestVo(notificationDetailVo));
        } catch (Exception e) {
            log.error("Failed to send sms. notificationId: {}",
                notificationDetailVo.getNotification().getNotificationId(), e);
            return SmsSendResultVo.UNKNOWN;
        }
    }

    private SmsRequestVo toSmsRequestVo(NotificationDetailVo notificationDetailVo) {
        Notification notification = notificationDetailVo.getNotification();
        return SmsRequestVo.of(
            notification.getMessageId(),
            notification.getSender().getPhoneNumber(),
            notification.getContent(),
            notificationDetailVo.getSmsRequests().stream()
                .map(it -> SmsRecipientRequestVo.of(
                    it.getMessageId(),
                    it.getRecipientPhoneNumber()
                ))
                .collect(Collectors.toList())
        );
    }

    public Page<Notification> getNotifications(Long adminMemberId, String searchWord, Pageable pageable) {
        return notificationService.getNotifications(adminMemberId, searchWord, pageable);
    }

    public NotificationDetailVo getNotificationDetail(Long adminMemberId, Long notificationId) {
        Notification notification = notificationService.getNotification(notificationId);
        return NotificationDetailVo.of(notification, notification.getSmsRequests());
    }
}
