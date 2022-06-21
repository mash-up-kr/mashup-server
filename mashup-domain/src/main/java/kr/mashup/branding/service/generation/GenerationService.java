package kr.mashup.branding.service.generation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.repository.generation.GenerationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenerationService {

    private final GenerationRepository generationRepository;

    @Transactional(readOnly = true)
    public Generation getByIdOrThrow(Long generationId) {
        return generationRepository.findById(generationId)
            .orElseThrow(() -> new NotFoundException(ResultCode.GENERATION_NOT_FOUND));
    }
}