package kr.mashup.branding.facade.schedule;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.schedule.request.ScheduleCreateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleFacadeService {

    private final ScheduleService scheduleService;
    private final GenerationService generationService;

    @Transactional
    public ScheduleResponse create(ScheduleCreateRequest req) {
        Generation generation =
            generationService.getByNumberOrThrow(req.getGenerationNumber());
        DateRange dateRange = DateRange.of(
            req.getStartedAt(),
            req.getEndedAt()
        );

        Schedule schedule = scheduleService.save(
            Schedule.of(generation, req.getName(), dateRange)
        );

        return ScheduleResponse.from(schedule);
    }
}
