package kr.mashup.branding.repository.schedule;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByGeneration(Generation generation, Sort sort);

    @Query("SELECT s FROM Schedule s WHERE s.isCounted = :isCounted")
    List<Schedule> findAllByIsCounted(@Param("isCounted") boolean isCounted);
}
/**
 * Schedule 연관관계
 * many to one: generation
 * one to many: event
 */