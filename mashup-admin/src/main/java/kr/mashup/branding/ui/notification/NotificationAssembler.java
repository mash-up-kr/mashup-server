package kr.mashup.branding.ui.notification;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.NotificationDetailVo;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.notification.sms.SmsSendRequestVo;
import kr.mashup.branding.ui.notification.sms.SmsRequestResponse;
import kr.mashup.branding.ui.notification.sms.SmsSendRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationAssembler {
    private final ApplicationService applicationService;

    SmsSendRequestVo toSmsSendVo(SmsSendRequest smsSendRequest) {
        return SmsSendRequestVo.of(
            smsSendRequest.getName(),
            smsSendRequest.getContent(),
            smsSendRequest.getApplicantIds()
        );
    }

    public NotificationSimpleResponse toNotificationResponse(Notification notification) {
        return new NotificationSimpleResponse(
            notification.getNotificationId(),
            notification.getStatus(),
            notification.getName(),
            notification.getContent()
        );
    }

    public NotificationDetailResponse toNotificationDetailResponse(NotificationDetailVo notificationDetailVo) {
        return new NotificationDetailResponse(
            notificationDetailVo.getNotification().getNotificationId(),
            notificationDetailVo.getSmsRequests().stream()
                .map(this::toSmsRequestResponse)
                .collect(Collectors.toList())
        );
    }

    public SmsRequestResponse toSmsRequestResponse(SmsRequest smsRequest) {
        String teamName = applicationService.getApplications(smsRequest.getRecipientApplicant().getApplicantId())
            .stream()
            .findFirst()
            .map(it -> it.getApplicationForm().getTeam().getName())
            .orElse(null);
        return new SmsRequestResponse(
            smsRequest.getSmsRequestId(),
            smsRequest.getNotification().getNotificationId(),
            smsRequest.getStatus(),
            smsRequest.getRecipientApplicant().getName(),
            smsRequest.getRecipientPhoneNumber(),
            teamName
        );
    }
}
