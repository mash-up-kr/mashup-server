package kr.mashup.branding.ui.schedule.response;

import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.ui.event.response.EventResponse;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Value(staticConstructor = "of")
public class ScheduleResponse {

    Long scheduleId;

    String name;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    Integer generationNumber;

    List<EventResponse> eventList;

    public static ScheduleResponse from(Schedule schedule) {
        return ScheduleResponse.of(
            schedule.getId(),
            schedule.getName(),
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
