package kr.mashup.branding.repository.attendance;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.schedule.Schedule;

@Repository
public interface CustomAttendanceRepository {
	List<Attendance> getBySchedule(Schedule schedule);
}
