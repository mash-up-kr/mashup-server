package kr.mashup.branding.repository.schedule;

import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    @Query("SELECT s FROM Schedule s WHERE s.isCounted = :isCounted")
    List<Schedule> findAllByIsCounted(@Param("isCounted") boolean isCounted);

    Optional<Schedule> findByIdAndStatus(Long id, ScheduleStatus status);
}
/**
 * Schedule 연관관계
 * many to one: generation
 * one to many: event
 */
