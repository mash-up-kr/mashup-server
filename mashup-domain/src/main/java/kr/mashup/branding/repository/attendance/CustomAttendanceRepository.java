package kr.mashup.branding.repository.attendance;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomAttendanceRepository {
	List<Attendance> findBySchedule(Schedule schedule);

	List<Attendance> findAllByEvent(Event event);
}
