package kr.mashup.branding.dto.content;

import java.time.LocalDateTime;

import lombok.Value;

@Value(staticConstructor = "of")
public class ContentCreateDto {

    String title;

    String content;

    LocalDateTime startedAt;

    Long eventId;
}
