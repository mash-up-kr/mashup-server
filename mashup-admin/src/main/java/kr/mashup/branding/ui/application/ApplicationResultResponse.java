package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationResultResponse {
    /**
     * 지원 상태
     */
    private ApplicationResultStatus status;
    /**
     * 면접 시작 시각
     */
    private LocalDateTime interviewStartedAt;
    /**
     * 면접 종료 시각
     */
    private LocalDateTime interviewEndedAt;
}
