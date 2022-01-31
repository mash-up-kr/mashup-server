package kr.mashup.branding.domain.sms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SmsRequestVo {
    private Long applicantId;
    private String applicantName;
    private String phoneNumber;

    public static SmsRequestVo of(Long applicantId, String applicantName, String phoneNumber) {
        return new SmsRequestVo(applicantId, applicantName, phoneNumber);
    }
}
