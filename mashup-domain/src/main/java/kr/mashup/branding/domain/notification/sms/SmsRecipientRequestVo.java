package kr.mashup.branding.domain.notification.sms;

import lombok.Value;

@Value(staticConstructor = "of")
public class SmsRecipientRequestVo {
    String messageId;
    String phoneNumber;
}
