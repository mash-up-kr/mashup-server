package kr.mashup.branding.ui.schedule.response;

import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleType;
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

    Integer dateCount;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    Integer generationNumber;

    // TODO: 앱 배포 완료 후 Location으로 type 변경
    LocationResponse location;

    ScheduleType scheduleType;

    String notice;

    List<EventResponse> eventList;

    public static ScheduleResponse from(Schedule schedule, Integer dateCount) {
        return ScheduleResponse.of(
            schedule.getId(),
            schedule.getName(),
            dateCount,
            schedule.getStartedAt(),
            schedule.getEndedAt(),
            schedule.getGeneration().getNumber(),
            schedule.getLocation() == null ? null : LocationResponse.from(schedule.getLocation()),
            schedule.getScheduleType(),
            schedule.getNotice(),
            schedule.getEventList()
                .stream()
                .map(EventResponse::from)
                .collect(Collectors.toList())
        );
    }
}
