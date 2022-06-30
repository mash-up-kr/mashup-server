package kr.mashup.branding.ui.qrcode.response;

import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class QrCreateResponse {
    String qrCodeUrl;
}
