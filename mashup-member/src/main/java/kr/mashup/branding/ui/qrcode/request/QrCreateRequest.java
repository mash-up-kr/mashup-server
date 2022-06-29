package kr.mashup.branding.ui.qrcode.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QrCreateRequest {
    private Long eventId;
    private String code;
    private LocalDateTime start;
    private LocalDateTime end;
}
