package kr.mashup.branding.ui.schedule.response;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.content.Content;
import lombok.Getter;
import lombok.Value;

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
            content.getContent(),
            content.getStartedAt()
        );
    }
}
