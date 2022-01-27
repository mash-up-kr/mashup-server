package kr.mashup.branding.domain.sms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SmsRequestStatus {
    IN_PROGRESS("발송중"), SUCCESS("성공"), FAIL("실패");

    private final String description;
}
