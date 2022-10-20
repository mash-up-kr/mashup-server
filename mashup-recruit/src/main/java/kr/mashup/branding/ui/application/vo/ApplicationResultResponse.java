package kr.mashup.branding.ui.application.vo;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class ApplicationResultResponse {
    /**
     * 지원 상태
     */
    private final ApplicationStatusResponse status;
    /**
     * 면접 시작 시각
     */
    private final ZonedDateTime interviewStartedAt;
    /**
     * 면접 종료 시각
     */
    private final ZonedDateTime interviewEndedAt;
    /**
     * 면접 안내 링크 (오픈채팅방 링크 or 화상미팅 링크)
     */
    private final String interviewGuideLink;
}
