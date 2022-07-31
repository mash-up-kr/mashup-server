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
import kr.mashup.branding.ui.schedule.response.ScheduleResponseListWithProgress;
import kr.mashup.branding.util.DateUtil;
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

    public ScheduleResponseListWithProgress getByGenerationNum(Integer number) {
        Generation generation = generationService.getByNumberOrThrow(number);

        List<Schedule> scheduleList = scheduleService.getByGeneration(generation);
        Progress progress;
        Integer dateCount = calculateDateCount(scheduleList);

        if (scheduleList.size() == 0) {
            progress = Progress.NOT_REGISTER;
        } else if (dateCount == -1L) {
            progress = Progress.DONE;
        } else {
            progress = Progress.ON_GOING;
        }

        List<ScheduleResponse> scheduleResponseList = scheduleList.stream()
            .map(ScheduleResponse::from)
            .collect(Collectors.toList());

        return ScheduleResponseListWithProgress.of(progress, dateCount, scheduleResponseList);
    }

    private Integer calculateDateCount(List<Schedule> scheduleList) {
        LocalDateTime currentTime = LocalDateTime.now();
        Integer dateCount = -1;

        int listSize = scheduleList.size();
        for (int i = listSize - 1; i > 0; i--) {
            LocalDateTime startedAt = scheduleList.get(i).getStartedAt();
            LocalDateTime endedAt = scheduleList.get(i).getEndedAt();

            if (isFinishedSchedule(currentTime, endedAt) || isOngoingSchedule(startedAt, endedAt, currentTime)) {
                break;
            }
            dateCount = Period.between(currentTime.toLocalDate(), startedAt.toLocalDate()).getDays();
        }

        return dateCount;
    }

    private Boolean isFinishedSchedule(LocalDateTime now, LocalDateTime endedAt) {
        return now.isAfter(endedAt);
    }

    private Boolean isOngoingSchedule(LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime now) {
        return DateUtil.isInTime(startedAt, endedAt, now);
    }
}
