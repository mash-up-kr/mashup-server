package kr.mashup.branding.dto.schedule;

import java.time.LocalDateTime;

import lombok.Value;

@Value(staticConstructor = "of")
public class ScheduleCreateDto {

    String name;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    Long generationId;
}
