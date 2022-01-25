package kr.mashup.branding.domain.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SmsRequestVo {
    private Long userId;
    private String username;
    private String content;
    private String phoneNumber;
}
