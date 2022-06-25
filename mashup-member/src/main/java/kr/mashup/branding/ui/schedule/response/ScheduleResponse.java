package kr.mashup.branding.ui.schedule.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.ui.event.response.EventResponse;
import lombok.Data;

@Data
public class ScheduleResponse {

	private final Long scheduleId;
	private final String name;
	private final LocalDateTime startedAt;
	private final LocalDateTime endedAt;
	private final Integer generationNumber;
	private final List<EventResponse> eventList;

	public static ScheduleResponse of(Schedule schedule) {
		return new ScheduleResponse(
			schedule.getId(),
			schedule.getName(),
			schedule.getStartedAt(),
			schedule.getEndedAt(),
			schedule.getGeneration().getNumber(),
			schedule.getEventList()
				.stream()
				.map(EventResponse::of)
				.collect(Collectors.toList())
		);
	}
}
