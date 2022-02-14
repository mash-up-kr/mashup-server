package kr.mashup.branding.infrastructure.sms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value(staticConstructor = "of")
public class ToastSmsRequest {
    /**
     * 발신 내용
     */
    @JsonProperty("body")
    String content;
    /**
     * 발신자 전화번호
     */
    @JsonProperty("sendNo")
    String senderPhoneNumber;
    /**
     * 메시지 식별자
     */
    @JsonProperty("senderGroupingKey")
    String notificationMessageId;
    /**
     * 수신자 정보 리스트
     */
    @JsonProperty("recipientList")
    List<ToastSmsRecipient> toastSmsRecipients;
}
