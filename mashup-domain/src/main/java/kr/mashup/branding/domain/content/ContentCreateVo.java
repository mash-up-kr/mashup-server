package kr.mashup.branding.domain.content;

import lombok.Value;

@Value(staticConstructor = "of")
public class ContentCreateVo {

    String content;

    Long eventId;
}
