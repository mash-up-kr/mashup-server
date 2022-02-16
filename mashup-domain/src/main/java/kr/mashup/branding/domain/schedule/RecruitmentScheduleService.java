package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface RecruitmentScheduleService {
    /**
     * 채용 일정 목록 조회
     */
    List<RecruitmentSchedule> getAll();

    /**
     * 채용 일정 생성
     */
    RecruitmentSchedule create(RecruitmentScheduleCreateVo recruitmentScheduleCreateVo);

    /**
     * 채용 일정 생성
     */
    RecruitmentSchedule update(Long recruitmentScheduleId, RecruitmentScheduleUpdateVo recruitmentScheduleUpdateVo);

    /**
     * 채용 일정 삭제
     */
    void delete(Long recruitmentScheduleId);

    /**
     * 모집 시작했는지
     */
    boolean isRecruitStarted(LocalDateTime localDateTime);

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
