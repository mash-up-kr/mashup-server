package kr.mashup.branding.repository.event;

import kr.mashup.branding.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
