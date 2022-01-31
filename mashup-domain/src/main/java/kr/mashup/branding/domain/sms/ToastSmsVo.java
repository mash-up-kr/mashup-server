package kr.mashup.branding.domain.sms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ToastSmsVo {
    private String key;
    private String phoneNumber;

    public static ToastSmsVo of(String key, String phoneNumber) {
        return new ToastSmsVo(key, phoneNumber);
    }
}
