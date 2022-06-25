package kr.mashup.branding.service.schedule;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleCreateVo;
import kr.mashup.branding.repository.schedule.ScheduleRepository;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;
	private final GenerationService generationService;

	public Schedule create(ScheduleCreateVo scheduleCreateVo) {
		Generation generation = generationService.getByIdOrThrow(scheduleCreateVo.getGenerationId());
		DateRange dateRange = DateRange.of(scheduleCreateVo.getStartedAt(), scheduleCreateVo.getEndedAt());

		Schedule schedule = Schedule.of(generation, scheduleCreateVo.getName(), dateRange);
		return scheduleRepository.save(schedule);
	}

	@Transactional(readOnly = true)
	public Schedule getByIdOrThrow(Long scheduleId) {
		return scheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new NotFoundException(ResultCode.SCHEDULE_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public List<Schedule> getByGenerationNumber(Integer generationNumber) {
		Generation generation = generationService.getByNumberOrThrow(generationNumber);
		return scheduleRepository.findByGeneration(generation, Sort.by(Sort.Direction.ASC, "startedAt"));
	}
}
