package kr.mashup.branding.ui.qrcode.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QrCreateRequest {
    private Long eventId;
    private String code;
    private LocalDateTime start;
    private LocalDateTime end;
}
