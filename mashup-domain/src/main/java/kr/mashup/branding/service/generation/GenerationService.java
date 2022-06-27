package kr.mashup.branding.service.generation;

import kr.mashup.branding.domain.generation.GenerationVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.repository.generation.GenerationRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GenerationService {

    private final GenerationRepository generationRepository;

    @Transactional(readOnly = true)
    public GenerationVo getGenerationInfoOrThrow(Integer number){
        Generation generation = generationRepository.findByNumber(number).orElseThrow(NotFoundException::new);
        return GenerationVo.from(generation);
    }
}
