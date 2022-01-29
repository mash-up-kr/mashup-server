package kr.mashup.branding.domain.sms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SmsRequestVo {
    private Long userId;
    private String username;
    private String phoneNumber;

    public static SmsRequestVo of(Long userId, String username, String phoneNumber) {
        return new SmsRequestVo(userId, username, phoneNumber);
    }
}
