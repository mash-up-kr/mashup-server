package kr.mashup.branding.facade.schedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.schedule.response.Progress;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import kr.mashup.branding.ui.schedule.response.ScheduleResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleFacadeService {

    private final ScheduleService scheduleService;
    private final GenerationService generationService;

    @Transactional(readOnly = true)
    public ScheduleResponse getById(Long id) {
        final Schedule schedule = scheduleService.getByIdAndStatusOrThrow(id, ScheduleStatus.PUBLIC);
        final Integer dateCount = countDayFromNow(schedule.getStartedAt(), LocalDateTime.now());

        return ScheduleResponse.from(schedule, dateCount);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseList getByGenerationNum(Integer generationNumber) {

        final Generation generation = generationService.getByNumberOrThrow(generationNumber);

        final List<Schedule> scheduleList = scheduleService.getByGenerationAndStatus(generation, ScheduleStatus.PUBLIC);

        final LocalDateTime currentTime = LocalDateTime.now();

        final List<ScheduleResponse> scheduleResponseList = new ArrayList<>();
        Optional<Integer> nextScheduleDayCountFromNow = Optional.empty();

        for(final Schedule schedule : scheduleList){

            final Integer dayCountFromNow = countDayFromNow(schedule.getStartedAt(), currentTime);

            nextScheduleDayCountFromNow = updateNextScheduleDayCountFromNow(nextScheduleDayCountFromNow, dayCountFromNow);

            final ScheduleResponse scheduleResponse = ScheduleResponse.from(schedule, dayCountFromNow);
            scheduleResponseList.add(scheduleResponse);
        }


        final Progress progress = calcuateScheduleProgress(generation, scheduleList, currentTime);

        return ScheduleResponseList.of(progress, nextScheduleDayCountFromNow, scheduleResponseList);
    }

    private static Optional<Integer> updateNextScheduleDayCountFromNow(
            final Optional<Integer> nextScheduleDayCountFromNow,
            final Integer dayCountFromNow) {

        return nextScheduleDayCountFromNow
                .map(integer -> Math.min(dayCountFromNow, integer))
                .or(() -> Optional.of(dayCountFromNow));
    }

    private static Progress calcuateScheduleProgress(Generation generation, List<Schedule> scheduleList, LocalDateTime currentTime) {
        if (scheduleList.size() == 0) {
            return Progress.NOT_REGISTERED;
        }
        final LocalDate generationEndDate = generation.getEndedAt();
        final LocalDate today = currentTime.toLocalDate();
        final Boolean isGenerationOngoing = generationEndDate.isAfter(today) || generationEndDate.isEqual(today);

        return isGenerationOngoing ? Progress.ON_GOING : Progress.DONE;
    }

    private Integer countDayFromNow(LocalDateTime startedAt, LocalDateTime currentTime) {
        return (int) ChronoUnit.DAYS.between(
                currentTime.truncatedTo(ChronoUnit.DAYS),
                startedAt.truncatedTo(ChronoUnit.DAYS)
        );
    }

}
