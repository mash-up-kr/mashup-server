package kr.mashup.branding.domain.sms.dto;

import lombok.Data;

import java.util.List;

@Data
public class ToastSmsResponse {
    private ToastSmsResponseHeader header;
    private ToastSmsResponseBody body;

    @Data
    public static class ToastSmsResponseHeader {
        private Boolean isSuccessful;
        private Integer resultCode;
        private String resultMessage;
    }

    @Data
    public static class ToastSmsResponseBody {
        private ToastSmsResponseBodyData data;
    }

    @Data
    public static class ToastSmsResponseBodyData {
        private String requestId;
        private Integer statusCode;
        private String senderGroupingKey;
        private List<ToastSendResult> sendResultList;
    }

    @Data
    public static class ToastSendResult {
        private String recipientNo;
        private Integer resultCode;
        private String resultMessage;
        private Integer recipientSeq;
        private String recipientGroupingKey;
    }
}
