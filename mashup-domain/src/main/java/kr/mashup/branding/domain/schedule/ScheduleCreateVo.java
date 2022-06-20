package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;

import lombok.Value;

@Value(staticConstructor = "of")
public class ScheduleCreateVo {

    String name;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    Long generationId;
}
