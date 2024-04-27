package kr.mashup.branding.ui.schedule.response;

import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class QrCodeResponse {
    @Deprecated
    private String qrCodeUrl; // 프론트에서 qrCode 를 이용해 생성하는 것으로 변경 예정
    private String attendanceCode;
}
