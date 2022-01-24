package kr.mashup.branding.domain.sms.dto;

import lombok.Data;

import java.util.List;

@Data
public class ToastSmsRequest {
    private String body;
    private String sendNo;
    private String senderGroupingKey;
    private List<Recipient> recipientList;

    @Data
    public static class Recipient {
        private String recipientNo;
        private String recipientGroupingKey;
    }
}
