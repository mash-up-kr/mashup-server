package kr.mashup.branding.domain.sms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SmsRequestGroupVo {
    private String content;
    private String description;

    public static SmsRequestGroupVo of(String content, String description) {
        return new SmsRequestGroupVo(content, description);
    }
}
