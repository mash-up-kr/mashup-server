package kr.mashup.branding.service.schedule;

import java.time.LocalDateTime;

import com.mysema.commons.lang.Assert;
import kr.mashup.branding.util.DateRange;
import lombok.Getter;

@Getter
public class EventCreateDto {

	private String eventName;
	private final DateRange dateRange;

	public static EventCreateDto of(String eventName, LocalDateTime startedAt, LocalDateTime endedAt){
		Assert.notNull(eventName,"'scheduleId' must not be null");
		Assert.notNull(startedAt,"'startedAt' must not be null");
		Assert.notNull(endedAt,"'endedAt' must not be null");
		DateRange dateRange = DateRange.of(startedAt, endedAt);

		return new EventCreateDto(eventName, dateRange);
	}

	private EventCreateDto(String eventName, DateRange dateRange) {
		this.eventName = eventName;
		this.dateRange = dateRange;
	}
}
