package kr.mashup.branding.repository.schedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    List<Schedule> findAllByIsCountedAndEndedAtIsBefore(Boolean isCounted, LocalDateTime at);

    Optional<Schedule> findByIdAndStatus(Long id, ScheduleStatus status);

    List<Schedule> findByGenerationAndStatusOrderByStartedAtAsc(Generation generation, ScheduleStatus status);
}
/**
 * Schedule 연관관계
 * many to one: generation
 * one to many: event
 */
