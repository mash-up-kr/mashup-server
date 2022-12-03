package kr.mashup.branding.ui.schedule.response;

import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class QrCodeResponse {
    String qrCodeUrl;
}
