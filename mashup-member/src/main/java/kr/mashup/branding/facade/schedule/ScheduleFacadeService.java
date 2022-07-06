package kr.mashup.branding.facade.schedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.schedule.request.ScheduleCreateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleFacadeService {

    private final ScheduleService scheduleService;
    private final GenerationService generationService;

    @Transactional
    public ScheduleResponse create(ScheduleCreateRequest req) {
        Generation generation =
            generationService.getByIdOrThrow(req.getGenerationId());
        DateRange dateRange = DateRange.of(
            req.getStartedAt(),
            req.getEndedAt()
        );

        Schedule schedule = scheduleService.save(
            Schedule.of(generation, req.getName(), dateRange)
        );

        return ScheduleResponse.from(schedule);
    }

    public ScheduleResponse getById(Long id) {
        Schedule schedule = scheduleService.getByIdOrThrow(id);

        return ScheduleResponse.from(schedule);
    }

    public List<ScheduleResponse> getByGenerationNum(Integer number) {
        Generation generation = generationService.getByNumberOrThrow(number);

        List<Schedule> scheduleList = scheduleService.getByGeneration(generation);

        return scheduleList.stream()
            .map(ScheduleResponse::from)
            .collect(Collectors.toList());
    }

}
