package kr.mashup.branding.domain.sms.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Data
public class ToastSmsRequest {
    private String body;
    private String sendNo;
    private String senderGroupingKey;
    private List<Recipient> recipientList;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Recipient {
        private String recipientNo;
        private String recipientGroupingKey;

        public static Recipient of(String recipientNo, String recipientGroupingKey){
            return new Recipient(recipientNo, recipientGroupingKey);
        }
    }
}
