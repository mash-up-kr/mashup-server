package kr.mashup.branding.repository.recruitmentschedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentScheduleRepository extends JpaRepository<RecruitmentSchedule, Long>, RecruitmentScheduleRepositoryCustom {
    boolean existsByGenerationAndEventName(Generation generation, RecruitmentScheduleEventName eventName);
}
