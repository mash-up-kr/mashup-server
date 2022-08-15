package kr.mashup.branding.ui.content.request;

import java.time.LocalDateTime;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ContentCreateRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private Long eventId;
}
