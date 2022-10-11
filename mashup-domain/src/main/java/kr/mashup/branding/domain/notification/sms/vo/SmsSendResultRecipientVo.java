package kr.mashup.branding.domain.notification.sms.vo;

import kr.mashup.branding.domain.notification.sms.SmsNotificationStatus;
import lombok.Value;

@Value(staticConstructor = "of")
public class SmsSendResultRecipientVo {
    /**
     * Toast SMS 에서 사용하는 요청 식별자. 발송 단건 조회시 사용
     */
    String requestId;
    /**
     * Toast 의 recipientGroupingKey. 수신자 기준으로 문자 발송 이력 조회시 사용
     */
    String messageId;
    /**
     * 발송 상태
     */
    SmsNotificationStatus status;
    /**
     * 결과 코드
     */
    String resultCode;
    /**
     * 결과 메시지
     */
    String resultMessage;
}
