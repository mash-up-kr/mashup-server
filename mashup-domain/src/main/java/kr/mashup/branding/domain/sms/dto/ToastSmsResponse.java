package kr.mashup.branding.domain.sms.dto;

import kr.mashup.branding.domain.sms.ToastResponseStatus;
import lombok.Data;

import java.security.PublicKey;
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
        /*
         * 요청 상태 코드(1:요청 중, 2:요청 완료, 3:요청 실패)
         * */
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

    public Boolean isInProgress() {
        return this.header.resultCode == ToastResponseStatus.IN_PROGRESS.getCode();
    }

    public Boolean isSuccess() {
        if(!this.header.isSuccessful) {
            return false;
        }

        if (this.header.resultCode == ToastResponseStatus.FAIL.getCode()) {
            return false;
        }
        return true;
    }
}
