package kr.mashup.branding.ui.schedule.response;

import java.time.LocalDateTime;
import java.util.List;

import kr.mashup.branding.ui.event.response.EventResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleResponse {

    private Long scheduleId;

    private String name;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private Integer generationNumber;

    private List<EventResponse> eventList;
}
