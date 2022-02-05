package kr.mashup.branding.domain;

import java.time.LocalDateTime;

public class MashupSchedule {
    private MashupSchedule() {
    }

    /**
     * 12기 모집 시작 시각 (inclusive)
     * TODO: 시간 정해야함
     */
    public static final LocalDateTime RECRUIT_STARTED_AT = LocalDateTime.of(2022, 3, 2, 0, 0);

    /**
     * 12기 모집 종료 날짜 (inclusive)
     */
    public static final LocalDateTime RECRUIT_ENDED_AT = LocalDateTime.of(2022, 3, 15, 23, 59, 59);

    /**
     * 12기 서류 결과 발표 시각 (inclusive)
     * TODO: 시간 정해야함
     */
    public static final LocalDateTime SCREENING_RESULT_ANNOUNCED_AT = LocalDateTime.of(2022, 3, 19, 0, 0);

    /**
     * 12기 면접 결과 발표 시각 (inclusive)
     * TODO: 시간 정해야함
     */
    public static final LocalDateTime INTERVIEW_RESULT_ANNOUNCED_AT = LocalDateTime.of(2022, 3, 29, 0, 0);
}
