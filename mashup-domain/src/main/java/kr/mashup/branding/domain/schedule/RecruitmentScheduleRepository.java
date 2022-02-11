package kr.mashup.branding.domain.schedule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentScheduleRepository extends JpaRepository<RecruitmentSchedule, Long> {
    Optional<RecruitmentSchedule> findByEventName(String eventName);

    boolean existsByEventName(String eventName);
}
