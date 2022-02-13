package kr.mashup.branding.ui.notification;

import java.util.List;

import kr.mashup.branding.ui.notification.sms.SmsRequestResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDetailResponse {
    private Long notificationId;
    private List<SmsRequestResponse> smsRequests;
}
