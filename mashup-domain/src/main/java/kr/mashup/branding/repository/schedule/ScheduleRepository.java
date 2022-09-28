package kr.mashup.branding.repository.schedule;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByGeneration(Generation generation, Sort sort);

    @Query("select s from Schedule s join m.memberGenerations mg where mg.generation = :generation  and m.status = 'ACTIVE'")
    Optional<Schedule> findAllActiveByGeneration(@Param("generation") Generation generation);
}
