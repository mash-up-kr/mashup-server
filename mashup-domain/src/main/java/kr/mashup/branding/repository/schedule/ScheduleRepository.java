package kr.mashup.branding.repository.schedule;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByGeneration(Generation generation, Sort sort);

    Schedule findFirstByOrderByCreatedAt();
}
