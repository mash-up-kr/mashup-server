package kr.mashup.branding.ui.event.request;

import java.time.LocalDateTime;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EventCreateRequest {

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @NotNull
    private Long scheduleId;
}
