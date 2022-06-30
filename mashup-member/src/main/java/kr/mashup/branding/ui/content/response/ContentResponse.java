package kr.mashup.branding.ui.content.response;

import kr.mashup.branding.domain.content.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class ContentResponse {

    Long contentId;

    String content;

    public static ContentResponse from(Content content) {
        return ContentResponse.of(
            content.getId(),
            content.getContent()
        );
    }
}
