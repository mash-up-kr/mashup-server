package kr.mashup.branding.infrastructure.sms;

import java.util.List;

import lombok.Data;

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
        /**
         * 요청 상태 코드(1:요청 중, 2:요청 완료, 3:요청 실패)
         */
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

    public boolean isSuccess() {
        if (!this.header.isSuccessful) {
            return false;
        }
        return ToastSmsResponseStatus.isSuccess(header.resultCode);
    }
}
