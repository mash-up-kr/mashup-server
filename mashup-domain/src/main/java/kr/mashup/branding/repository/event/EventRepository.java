package kr.mashup.branding.repository.event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.schedule.Schedule;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findBySchedule(Schedule schedule);
}
