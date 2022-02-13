package kr.mashup.branding.infrastructure.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value(staticConstructor = "of")
public class ToastSmsRecipient {
    /**
     * 수신자 전화번호
     */
    @JsonProperty("recipientNo")
    String recipientPhoneNumber;
    /**
     * 메시지 식별자
     */
    @JsonProperty("recipientGroupingKey")
    String smsMessageId;
}
