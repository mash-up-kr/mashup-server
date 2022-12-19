package kr.mashup.branding.ui.schedule.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QrCodeGenerateRequest {

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
