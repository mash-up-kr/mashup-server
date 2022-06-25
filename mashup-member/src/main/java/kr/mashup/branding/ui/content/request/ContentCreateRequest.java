package kr.mashup.branding.ui.content.request;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ContentCreateRequest {

    @NotNull
    private String content;

    @NotNull
    private Long eventId;
}
