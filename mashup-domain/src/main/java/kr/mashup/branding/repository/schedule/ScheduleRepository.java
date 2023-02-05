package kr.mashup.branding.repository.schedule;

import kr.mashup.branding.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    @Query("SELECT s FROM Schedule s WHERE s.isCounted = :isCounted")
    List<Schedule> findAllByIsCounted(@Param("isCounted") boolean isCounted);

    Schedule findByStartedAt(LocalDateTime startedAt);
}
/**
 * Schedule 연관관계
 * many to one: generation
 * one to many: event
 */
