package kr.mashup.branding.ui.qrcode.request;

import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Getter
@Value(staticConstructor = "of")
public class QrCreateRequest {
    Long eventId;

    String code;

    LocalDateTime start;

    LocalDateTime end;
}
