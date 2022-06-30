package kr.mashup.branding.service.content;

import kr.mashup.branding.domain.content.Content;
import kr.mashup.branding.repository.content.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public Content save(Content content) {
        return contentRepository.save(content);
    }
}
