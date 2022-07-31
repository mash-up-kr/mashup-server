package kr.mashup.branding.facade.schedule;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.schedule.response.Progress;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import kr.mashup.branding.ui.schedule.response.ScheduleResponseList;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleFacadeService {

    private final ScheduleService scheduleService;
    private final GenerationService generationService;

    public ScheduleResponse getById(Long id) {
        Schedule schedule = scheduleService.getByIdOrThrow(id);
        Integer dayCount = countDate(schedule.getStartedAt(), LocalDateTime.now());

        return ScheduleResponse.from(schedule, dayCount);
    }

    public ScheduleResponseList getByGenerationNum(Integer number) {
        Generation generation = generationService.getByNumberOrThrow(number);

        List<Schedule> scheduleList = scheduleService.getByGeneration(generation);

        LocalDateTime currentTIme = LocalDateTime.now();
        List<ScheduleResponse> scheduleResponseList = scheduleList.stream()
            .map(schedule -> ScheduleResponse.from(
                schedule,
                countDate(schedule.getStartedAt(), currentTIme)
            ))
            .collect(Collectors.toList());

        Progress progress;
        Integer dateCount = pickNextScheduleDate(scheduleResponseList);

        if (scheduleList.size() == 0) {
            progress = Progress.NOT_REGISTERED;
        } else {
            progress = checkScheduleProgress(dateCount);
        }

        return ScheduleResponseList.of(progress, dateCount, scheduleResponseList);
    }

    private Integer countDate(LocalDateTime startedAt, LocalDateTime currentTime) {

        return Period.between(currentTime.toLocalDate(), startedAt.toLocalDate()).getDays();
    }

    private Integer pickNextScheduleDate(List<ScheduleResponse> scheduleResponseList) {
        Integer dateCount = 0;

        for (ScheduleResponse scheduleResponse : scheduleResponseList) {
            if (scheduleResponse.getDatCount() > 0) {
                dateCount = scheduleResponse.getDatCount();
                break;
            }
        }

        return dateCount;
    }

    private Progress checkScheduleProgress(Integer dateCount) {
        Progress progress;
        if (dateCount == 0) {
            progress = Progress.DONE;
        } else {
            progress = Progress.ON_GOING;
        }

        return progress;
    }
}
