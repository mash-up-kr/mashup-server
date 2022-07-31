package kr.mashup.branding.ui.schedule.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.schedule.Schedule;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class ScheduleResponse {

    Long scheduleId;

    String name;

    Integer datCount;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    Integer generationNumber;

    List<EventResponse> eventList;

    public static ScheduleResponse from(Schedule schedule, Integer dayCount) {
        return ScheduleResponse.of(
            schedule.getId(),
            schedule.getName(),
            dayCount,
            schedule.getStartedAt(),
            schedule.getEndedAt(),
            schedule.getGeneration().getNumber(),
            schedule.getEventList()
                .stream()
                .map(EventResponse::from)
                .collect(Collectors.toList())
        );
    }
}
