package kr.mashup.branding.repository.schedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface ScheduleRepositoryCustom {

    Page<Schedule> findByGeneration(Generation generation, String searchWord, ScheduleStatus status, Pageable pageable);

    Optional<Schedule> retrieveByStartDate(LocalDate startDate);
}
