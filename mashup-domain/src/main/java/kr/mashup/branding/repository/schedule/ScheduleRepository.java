package kr.mashup.branding.repository.schedule;

import kr.mashup.branding.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    List<Schedule> findAllByIsCounted(Boolean isCounted);

    Schedule findByStartedAt(LocalDateTime startedAt);
}
/**
 * Schedule 연관관계
 * many to one: generation
 * one to many: event
 */
