package kr.mashup.branding.domain;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class MashupScheduleService {

    /**
     * 12기 모집 시작 시각 (inclusive)
     * TODO: 시간 정해야함
     */
    private static final LocalDateTime RECRUIT_STARTED_AT = LocalDateTime.of(2022, 3, 2, 0, 0);

    /**
     * 12기 모집 종료 날짜 (inclusive)
     */
    private static final LocalDateTime RECRUIT_ENDED_AT = LocalDateTime.of(2022, 3, 15, 23, 59, 59);

    /**
     * 12기 서류 결과 발표 시각 (inclusive)
     * TODO: 시간 정해야함
     */
    private static final LocalDateTime SCREENING_RESULT_ANNOUNCED_AT = LocalDateTime.of(2022, 3, 19, 0, 0);

    /**
     * 12기 면접 결과 발표 시각 (inclusive)
     * TODO: 시간 정해야함
     */
    private static final LocalDateTime INTERVIEW_RESULT_ANNOUNCED_AT = LocalDateTime.of(2022, 3, 29, 0, 0);

    /**
     * 서류 제출 가능한 시각인지
     */
    public boolean isRecruitAvailable(LocalDateTime localDateTime) {
        return !localDateTime.isBefore(RECRUIT_STARTED_AT) && !localDateTime.isAfter(RECRUIT_ENDED_AT);
    }

    /**
     * 서류 결과 보여주어도 되는 시각인지
     */
    public boolean canAnnounceScreeningResult(LocalDateTime localDateTime) {
        return !localDateTime.isBefore(SCREENING_RESULT_ANNOUNCED_AT);
    }

    /**
     * 면접 결과 보여주어도 되는 시각인지
     */
    public boolean canAnnounceInterviewResult(LocalDateTime localDateTime) {
        return !localDateTime.isBefore(INTERVIEW_RESULT_ANNOUNCED_AT);
    }
}
