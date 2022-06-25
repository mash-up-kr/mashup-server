package kr.mashup.branding.service.content;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.content.Content;
import kr.mashup.branding.domain.content.ContentCreateVo;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.repository.content.ContentRepository;
import kr.mashup.branding.service.event.EventService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final EventService eventService;

    @Transactional
    public Content create(ContentCreateVo contentCreateVo) {
        Event event = eventService.getByIdOrThrow(contentCreateVo.getEventId());
        Content content = Content.of(event, contentCreateVo.getContent());
        return contentRepository.save(content);
    }
}
