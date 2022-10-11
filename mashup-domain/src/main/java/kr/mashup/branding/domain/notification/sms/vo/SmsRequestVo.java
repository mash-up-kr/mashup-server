package kr.mashup.branding.domain.notification.sms.vo;

import java.util.List;

import lombok.Value;

@Value(staticConstructor = "of")
public class SmsRequestVo {
    String messageId;
    String senderPhoneNumber;
    String content;
    List<SmsRecipientRequestVo> smsRecipientRequestVos;
}
