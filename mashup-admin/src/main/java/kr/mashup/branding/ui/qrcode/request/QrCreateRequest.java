package kr.mashup.branding.ui.qrcode.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class QrCreateRequest {
    Long eventId;

    String code;

    LocalDateTime start;

    LocalDateTime end;
}
