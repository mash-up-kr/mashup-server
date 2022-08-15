package kr.mashup.branding.dto.event;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Value;

@Value(staticConstructor = "of")
public class EventCreateDto {

	LocalDateTime startedAt;

	LocalDateTime endedAt;

	Long scheduleId;
}
