package kr.mashup.branding.scorehistory;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.repository.schedule.ScheduleRepository;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ScoreHistoryTasklet implements Tasklet {
	private final ScheduleRepository scheduleRepository;
	private final AttendanceService attendanceService;
	private final ScoreHistoryService scoreHistoryService;

	@Override
	@Transactional
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		List<Schedule> schedules = scheduleRepository.findAllByIsCounted(false);

		schedules.forEach(schedule -> {
			attendanceService.getByScheduleAndGroupByMember(schedule).forEach((member, attendances) ->
				scoreHistoryService.save(scoreHistoryService.createByAttendances(member, schedule, attendances)));
			schedule.changeIsCounted(true);
		});
		return RepeatStatus.FINISHED;
	}
}
