package kr.mashup.branding.ui.schedule.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ScheduleUpdateRequest {

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @NotEmpty
    private List<EventCreateRequest> eventsCreateRequests;
}