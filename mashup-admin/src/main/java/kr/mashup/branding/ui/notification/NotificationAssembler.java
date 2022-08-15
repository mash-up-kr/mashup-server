package kr.mashup.branding.ui.notification;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.NotificationDetailVo;
import kr.mashup.branding.domain.notification.sms.SmsNotificationStatus;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.notification.sms.SmsSendRequestVo;
import kr.mashup.branding.ui.notification.sms.SmsRequestAssembler;
import kr.mashup.branding.ui.notification.sms.SmsSendRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationAssembler {
    private final ApplicationService applicationService;
    private final SmsRequestAssembler smsRequestAssembler;

    SmsSendRequestVo toSmsSendVo(SmsSendRequest smsSendRequest) {
        return SmsSendRequestVo.of(
            smsSendRequest.getName(),
            smsSendRequest.getContent(),
            smsSendRequest.getApplicantIds()
        );
    }

    public NotificationSimpleResponse toNotificationResponse(Notification notification) {
        Map<SmsNotificationStatus, Integer> statusCountMap = notification.getSmsRequests()
            .stream()
            .collect(Collectors.toMap(
                SmsRequest::getStatus,
                it -> 1,
                Integer::sum
            ));
        return new NotificationSimpleResponse(
            notification.getNotificationId(),
            notification.getStatus(),
            notification.getName(),
            notification.getSenderPhoneNumber(),
            notification.getSentAt(),
            notification.getSender().getPosition(),
            statusCountMap.getOrDefault(SmsNotificationStatus.SUCCESS, 0),
            statusCountMap.getOrDefault(SmsNotificationStatus.FAILURE, 0),
            statusCountMap.values().stream().mapToInt(it -> it).sum()
        );
    }

    public NotificationDetailResponse toNotificationDetailResponse(NotificationDetailVo notificationDetailVo) {
        Notification notification = notificationDetailVo.getNotification();
        Map<SmsNotificationStatus, Integer> statusCountMap = notification.getSmsRequests()
            .stream()
            .collect(Collectors.toMap(
                SmsRequest::getStatus,
                it -> 1,
                Integer::sum
            ));
        return new NotificationDetailResponse(
            notification.getNotificationId(),
            notification.getStatus(),
            notification.getName(),
            notification.getContent(),
            notification.getSenderPhoneNumber(),
            notification.getSentAt(),
            notification.getSender().getPosition(),
            statusCountMap.getOrDefault(SmsNotificationStatus.SUCCESS, 0),
            statusCountMap.getOrDefault(SmsNotificationStatus.FAILURE, 0),
            statusCountMap.values().stream().mapToInt(it -> it).sum(),
            notificationDetailVo.getSmsRequests().stream()
                .map(smsRequestAssembler::toSmsRequestResponse)
                .collect(Collectors.toList())
        );
    }
}
