package kr.mashup.branding.ui.qrcode.response;

import lombok.Data;

@Data
public class QrCreateResponse {
    private final String qrCodeUrl;

    public static QrCreateResponse from(String url) {
        return new QrCreateResponse(url);
    }
}
