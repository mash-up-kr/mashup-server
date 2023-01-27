package kr.mashup.branding.ui.schedule.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @NotEmpty
    private List<EventCreateRequest> eventsCreateRequests;

    private ScheduleCreateRequest(String name, LocalDateTime startedAt, LocalDateTime endedAt, List<EventCreateRequest> eventsCreateRequests) {
        this.name = name;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.eventsCreateRequests = eventsCreateRequests;
    }


    public static ScheduleCreateRequest from(ScheduleUpdateRequest request){
        return new ScheduleCreateRequest(request.getName(), request.getStartedAt(), request.getEndedAt(), request.getEventsCreateRequests());
    }

}
