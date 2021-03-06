package kr.mashup.branding.facade.schedule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleFacadeService {

    private final ScheduleService scheduleService;
    private final GenerationService generationService;

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
