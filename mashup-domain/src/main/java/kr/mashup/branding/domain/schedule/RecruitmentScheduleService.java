package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;

public interface RecruitmentScheduleService {
    /**
     * 서류 제출 가능한 시각인지
     */
    boolean isRecruitAvailable(LocalDateTime localDateTime);

    /**
     * 서류 결과 보여주어도 되는 시각인지
     */
    boolean canAnnounceScreeningResult(LocalDateTime localDateTime);

    /**
     * 면접 결과 보여주어도 되는 시각인지
     */
    boolean canAnnounceInterviewResult(LocalDateTime localDateTime);
}
