package kr.mashup.branding.domain.event;

import java.time.LocalDateTime;

import com.sun.istack.NotNull;

import lombok.Value;

@Value(staticConstructor = "of")
public class EventCreateVo {

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    Long scheduleId;
}
