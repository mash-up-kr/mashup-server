package kr.mashup.branding.domain.notification.sms.vo;

import lombok.Value;

@Value(staticConstructor = "of")
public class SmsSendRequestVo {
    String name;
    String content;
}
