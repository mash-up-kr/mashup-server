package kr.mashup.branding.domain.notification;

import java.util.List;

import kr.mashup.branding.domain.notification.sms.SmsRequest;
import lombok.Value;

@Value(staticConstructor = "of")
public class NotificationDetailVo {
    Notification notification;
    List<SmsRequest> smsRequests;
}
