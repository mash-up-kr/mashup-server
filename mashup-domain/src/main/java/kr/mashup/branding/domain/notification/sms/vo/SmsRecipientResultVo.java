package kr.mashup.branding.domain.notification.sms.vo;

import kr.mashup.branding.domain.notification.sms.SmsNotificationStatus;
import lombok.Value;

@Value
public class SmsRecipientResultVo {
    /**
     * 메시지 식별자
     */
    String messageId;
    /**
     * 상태
     */
    SmsNotificationStatus status;
    /**
     * 에러 코드
     */
    String errorCode;
    /**
     * 에러 메시지
     */
    String errorMessage;
}
