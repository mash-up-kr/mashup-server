package kr.mashup.branding.ui.schedule.request;

import kr.mashup.branding.service.schedule.EventCreateDto;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class EventCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @NotEmpty
    private List<ContentsCreateRequest> contentsCreateRequests;

    public EventCreateDto toEventCreateDto(){
        return EventCreateDto.of(name, startedAt, endedAt);
    }
}
