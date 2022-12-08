package kr.mashup.branding.repository.event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.Schedule;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findBySchedule(Schedule schedule);

}
/**
 * Event 연관관계
 * many to one : schedule
 * one to many : content
 * one to one: attendanceCode
 */