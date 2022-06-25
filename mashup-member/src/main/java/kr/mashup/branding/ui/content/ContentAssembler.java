package kr.mashup.branding.ui.content;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.content.Content;
import kr.mashup.branding.ui.content.response.ContentResponse;

@Component
public class ContentAssembler {
    public ContentResponse toContentResponse(Content content) {
        return new ContentResponse(
            content.getId(),
            content.getContent()
        );
    }
}
