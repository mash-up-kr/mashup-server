package kr.mashup.branding.service.schedule;

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
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final GenerationService generationService;

    @Transactional
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
}
