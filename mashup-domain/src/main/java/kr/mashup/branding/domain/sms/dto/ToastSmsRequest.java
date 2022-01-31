package kr.mashup.branding.domain.sms.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Data
public class ToastSmsRequest {
    /*
     * 발신 내용
     * */
    private String body;
    /*
     * 발신자 전화번호
     * */
    private String sendNo;
    /*
     * 발신 키 (${profile}-${smsRequestGroupId})
     * */
    private String senderGroupingKey;
    /*
     * 수신자 정보 리스트
     * */
    private List<Recipient> recipientList;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Recipient {
        /*
        * 수신자 전화번호
        * */
        private String recipientNo;
        /*
         * 수신자 키 (${profile}-${smsRequestId})
         * */
        private String recipientGroupingKey;

        public static Recipient of(String recipientNo, String recipientGroupingKey){
            return new Recipient(recipientNo, recipientGroupingKey);
        }
    }
}
