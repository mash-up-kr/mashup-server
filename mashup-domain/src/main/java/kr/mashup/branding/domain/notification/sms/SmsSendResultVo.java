package kr.mashup.branding.domain.notification.sms;

import java.util.Collections;
import java.util.List;

import kr.mashup.branding.domain.notification.NotificationStatus;
import lombok.Value;

@Value(staticConstructor = "of")
public class SmsSendResultVo {

    public static final SmsSendResultVo UNKNOWN;

    static {
        UNKNOWN = new SmsSendResultVo(
            null,
            null,
            NotificationStatus.UNKNOWN,
            Collections.emptyList(),
            null,
            null
        );
    }

    /**
     * Toast SMS 에서 사용하는 요청 식별자. 발송 단건 조회시 사용
     */
    String resultId;
    /**
     * Toast 의 senderGroupingKey. 발송자 기준으로 문자 발송 목록 조회시 사용
     */
    String messageId;
    /**
     * 발송 상태
     */
    NotificationStatus status;
    /**
     * 수신자 별 결과
     */
    List<SmsSendResultRecipientVo> recipientResultVos;
    /**
     * 결과 코드
     */
    String resultCode;
    /**
     * 결과 메시지
     */
    String resultMessage;
}
