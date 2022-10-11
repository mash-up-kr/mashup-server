package kr.mashup.branding.ui.notification;

import java.util.Map;
import java.util.stream.Collectors;

import kr.mashup.branding.ui.notification.vo.NotificationDetailResponse;
import kr.mashup.branding.ui.notification.vo.NotificationSimpleResponse;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.vo.NotificationDetailVo;
import kr.mashup.branding.domain.notification.sms.SmsNotificationStatus;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendRequestVo;
import kr.mashup.branding.ui.notification.sms.SmsRequestAssembler;
import kr.mashup.branding.ui.notification.vo.SmsSendRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationAssembler {

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
