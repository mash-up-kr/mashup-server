package kr.mashup.branding.ui.qrcode.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class QrCodeResponse {
    @ApiModelProperty(required = true)
    String qrCodeUrl;
}
