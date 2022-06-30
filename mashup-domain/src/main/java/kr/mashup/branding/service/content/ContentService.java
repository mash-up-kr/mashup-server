package kr.mashup.branding.service.content;

import kr.mashup.branding.domain.content.Content;
import kr.mashup.branding.repository.content.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional
    public Content save(Content content) {
        return contentRepository.save(content);
    }
}
