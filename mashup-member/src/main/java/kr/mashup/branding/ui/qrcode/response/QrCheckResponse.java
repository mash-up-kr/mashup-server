package kr.mashup.branding.ui.qrcode.response;

import lombok.Data;

@Data
public class QrCheckResponse {
    private final boolean isAvailable;

    public static QrCheckResponse from(boolean isAvailable) {
        return new QrCheckResponse(isAvailable);
    }
}
