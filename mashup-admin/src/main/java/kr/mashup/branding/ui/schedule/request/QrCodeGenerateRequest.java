package kr.mashup.branding.ui.schedule.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QrCodeGenerateRequest {

    private LocalDateTime attendanceCheckStartedAt;     // 출석체크 시작시간
    private LocalDateTime attendanceCheckEndedAt;       // 출석체크 마감시간
    private LocalDateTime latenessCheckEndedAt;         // 지각체크 마감시간
}
