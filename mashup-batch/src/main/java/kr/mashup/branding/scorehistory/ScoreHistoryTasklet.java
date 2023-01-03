package kr.mashup.branding.scorehistory;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.pushnoti.vo.SeminarAttendanceAppliedVo;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.member.MemberService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
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
	private final PushNotiEventPublisher pushNotiEventPublisher;
	private final MemberService memberService;

	@Override
	@Transactional
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		List<Schedule> schedules = scheduleRepository.findAllByIsCounted(false);

		schedules.forEach(schedule -> {
			Map<Member, List<Attendance>> attendanceMap = attendanceService.getByScheduleAndGroupByMember(schedule);
			attendanceMap.forEach((member, attendances) -> {
				ScoreHistory scoreHistory = scoreHistoryService.createByAttendances(member, schedule, attendances);
				scoreHistoryService.save(scoreHistory);
			});
			schedule.changeIsCounted(true);
		});
		pushNotiEventPublisher.publishPushNotiSendEvent(
			new SeminarAttendanceAppliedVo(memberService.getAllPushNotiTargetableFcmTokens())
		);
		return RepeatStatus.FINISHED;
	}
}
