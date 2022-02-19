package kr.mashup.branding.domain.notification;

import kr.mashup.branding.domain.adminmember.AdminMember;
import lombok.Value;

@Value(staticConstructor = "of")
public class NotificationCreateVo {
    AdminMember senderAdminMember;
    String notificationName;
    String notificationContent;
}
