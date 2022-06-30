package kr.mashup.branding.dto.content;

import lombok.Value;

@Value(staticConstructor = "of")
public class ContentCreateDto {

    String content;

    Long eventId;
}
