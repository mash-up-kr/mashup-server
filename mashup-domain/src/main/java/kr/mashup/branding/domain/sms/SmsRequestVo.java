package kr.mashup.branding.domain.sms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SmsRequestVo {
    private Long applicantId;
    private String smsSendKey;
    private String applicantName;
    private String phoneNumber;

    public static SmsRequestVo of(Long applicantId, String smsSendKey, String applicantName, String phoneNumber) {
        return new SmsRequestVo(applicantId, smsSendKey, applicantName, phoneNumber);
    }
}
