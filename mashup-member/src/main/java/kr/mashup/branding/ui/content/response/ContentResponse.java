package kr.mashup.branding.ui.content.response;

import kr.mashup.branding.domain.content.Content;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ContentResponse {

    private final Long contentId;
    private final String content;

    public static ContentResponse from(Content content) {
        return new ContentResponse(content.getId(), content.getContent());
    }
}
