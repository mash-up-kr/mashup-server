package kr.mashup.branding.repository.recruitmentschedule;

import java.util.Optional;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentScheduleRepository extends JpaRepository<RecruitmentSchedule, Long> {
    Optional<RecruitmentSchedule> findByEventName(String eventName);

    boolean existsByEventName(String eventName);
}
