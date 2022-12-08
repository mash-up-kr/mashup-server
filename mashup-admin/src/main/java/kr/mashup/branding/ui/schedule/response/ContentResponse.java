package kr.mashup.branding.ui.schedule.response;

import kr.mashup.branding.domain.schedule.Content;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Getter
@Value(staticConstructor = "of")
public class ContentResponse {

    Long contentId;

    String title;

    String content;

    LocalDateTime startedAt;

    public static ContentResponse from(Content content) {
        return ContentResponse.of(
            content.getId(),
            content.getTitle(),
            content.getDesc(),
            content.getStartedAt()
        );
    }
}
