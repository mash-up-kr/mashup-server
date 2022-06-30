package kr.mashup.branding.facade.content;

import kr.mashup.branding.domain.content.Content;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.service.content.ContentService;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.ui.content.request.ContentCreateRequest;
import kr.mashup.branding.ui.content.response.ContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentFacadeService {

    private final ContentService contentService;
    private final EventService eventService;

    @Transactional
    public ContentResponse create(ContentCreateRequest req) {
        Event event = eventService.getByIdOrThrow(req.getEventId());

        Content content = contentService.save(
            Content.of(event, req.getContent())
        );

        return ContentResponse.from(content);
    }

}
